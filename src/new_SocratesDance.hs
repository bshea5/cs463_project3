module Main where

import Control.Concurrent
import System.Random
import System.Environment

data DanceCard 	= DC [Int]								deriving (Show)
data Message 	= MSG (Dancer, Int)
data Mailbox 	= MB (MVar Message)
data Dancer 	= Follower Int Mailbox DanceCard | 
				  Leader Int [Dancer] Mailbox DanceCard

-- must have the dances defined, 8 of them
-- will have to write this in 'main' too
dances = ["Waltz","Tango","Foxtrot","Quickstep",
		  "Rumba","Samba","Cha Cha","Jive"]

-- sets all values of a list to zero
reset :: [Int] -> [Int]
reset list = [x*0 | x <- [1..length list]]

-- finds if there is an instance of val
findInstance :: [Int] -> Int -> Bool
findInstance list val
	| null list        = False
	| head list == val = True
	| otherwise        = findInstance (tail list) val

-- mark a 1 at some index in the list
markIndex :: [Int] -> Int -> [Int]
markIndex list index = (fst split) ++ [1] ++ (tail $ snd split)
 	where
		split = splitAt index list

-- Card Stuff ==================================================

-- check card to see if you've danced some song
dancedSong :: DanceCard -> Int -> Bool
dancedSong (DC card) dance = (card!!dance) /= 0

-- how many times have you danced with someone
dancedWith :: DanceCard -> Dancer -> Int
dancedWith dc (Follower id m d) 		= dancedWithID dc id 0
dancedWith dc (Leader id followers m d) = dancedWithID dc id 0
	
dancedWithID :: DanceCard -> Int -> Int -> Int
dancedWithID (DC card) id count
	| null card 		= count
	| head card == id 	= dancedWithID (DC (tail card)) id (count+1)
	| otherwise 		= dancedWithID (DC (tail card)) id count

markCard :: DanceCard -> Int -> Int -> DanceCard
markCard (DC x) dance_num dancerID = (DC $ (fst split) ++ [dancerID] ++ (tail $ snd split))
	where
		split = splitAt dance_num x
		-- parse error on second when it was down here for some reason

showCard :: DanceCard -> String	
showCard (DC x) = showCardH x ""

-- assumes 8 dances 
showCardH :: [Int] -> String -> String
showCardH [] s = s ++ "\n"
showCardH (x:xs) s = s ++ (dances!!dance_num) ++ " with " ++ (show x) ++ "\n" ++ (showCardH xs s)
	where dance_num = (length dances) - (length (x:xs))

-- ==============================================================

-- Mailbox stuff ================================================

-- getMail :: Mailbox -> IO (MVar a)
-- getMail (MB mv) 
---- getMail (MB mv) = do
--	contents <- takeMVar mv
--	return contents


-- ==============================================================

-- Dancer stuff =================================================

-- instance Dancer Follower where
-- instance Dancer Leader where

follower :: Int -> Mailbox -> DanceCard -> IO ()
follower id (MB mv) card = loop
	where
		loop = do
			-- get message from mailbox
			(MSG ((Leader l_id followers (MB l_mv) card), dance)) <- takeMVar mv
			-- check contents of message & send message back to leader
			if (dancedSong card dance) 
				then putMVar l_mv (MSG ((Follower id (MB mv) card), -1))		-- no
			else if (dancedWithID card l_id 0) > 2 
				then putMVar l_mv (MSG ((Follower id (MB mv) card), -1))		-- no
			else do
				let marked_card = markCard card dance l_id 
				putMVar l_mv (MSG ((Follower id (MB mv) marked_card), dance))	-- yes
			-- followers don't stop for now. so loop
			loop 
			-- where
			--	respond_no 	= (MSG ((Leader id followers (MB l_mv) card)), -1)
			--	respond_yes = (MSG ((Leader id followers (MB l_mv) card)), id)
			-- send a kill message to followers to stop?

-- leader :: Int -> [Dancer] -> Mailbox -> DanceCard -> IO ()
-- might have to do leader with a state?

leaderH :: Int -> [Dancer] -> Mailbox -> DanceCard -> Int -> [Int] -> IO ()
leaderH id followers (MB mv) card current_dance peopleAsked
	| current_dance > 8 					= putStr $ showCard card 	-- give back card contents
	| (findInstance peopleAsked 0) == False 
		= leaderH id followers (MB mv) card (current_dance+1) (reset peopleAsked)	-- asked everyone, go to next song
	| otherwise 							= do
		-- pick a dancer
		index <- randomRIO (0,((length followers)-1))
		let (Follower f_id (MB f_mv) f_card) = followers!!index
		-- ask them to dance
		putMVar f_mv (MSG ((Leader id followers (MB mv) card), current_dance))
		-- get a response
		(MSG (dancer, response)) <- takeMVar mv
		-- check if already asked for this dance, than try asking
		if (peopleAsked!!index) == 1 then do
			putStrLn "already asked"
			leaderH id followers (MB mv) card current_dance peopleAsked
		else if response == (-1) then do
			putStrLn "Denied!"
			let new_peopleAsked = markIndex peopleAsked index 
			leaderH id followers (MB mv) card current_dance new_peopleAsked
		-- follower said yes, mark card and move on to next dance
		else do
			putStrLn "She said yes!"
			let markedCard 		= markCard card current_dance index
			let new_peopleAsked = reset peopleAsked
			leaderH id followers (MB mv) markedCard (current_dance+1) new_peopleAsked
		-- repeat

dance :: Dancer -> IO ()
dance (Follower id mailbox card) 			= follower id mailbox card
dance (Leader id followers mailbox card) 	= leaderH id followers mailbox card 0 peopleAsked
	where peopleAsked = [x*0 | x<-[0..length followers]]

-- ==============================================================
-- startDance
	-- start m followers   so  forkIO $ follower...
	-- start n leaders		   forkIO $ leader....
	-- wait for them to dance it out
	-- when leaders are done, end it all
	-- print results


main :: IO ()
main = do 
	argsList <- getArgs
	let n = (read (argsList !! 0) :: Int)
	let	m = (read (argsList !! 1) :: Int)
	let	emptyCard = [0,0,0,0,0,0,0,0] 	-- 8 dances
	emptyMVar <- newEmptyMVar

	-- create data for m followers and n leaders
	let	followerList = [(Follower id 			  (MB emptyMVar) (DC emptyCard)) | id<-[0..(m-1)]]
	let	leaderList 	 = [(Leader   id followerList (MB emptyMVar) (DC emptyCard)) | id<-[0..(n-1)]]

	-- must bind in order to run according to Dr. Snyder
	followersDancing <- mapM forkIO (map dance followerList)
	leadersDancing 	 <- mapM forkIO (map dance leaderList)

	--map putStr leadersDancing

	threadDelay $ 1000*5000
	putStr "Done.\n"




-- Notes
-- to compile use
-- 	ghc --make -o executeDancing new_SocratesDance.hs
-- then run using
-- 	./executeDancing n m





