import Control.Concurrent
import System.Random

data DanceCard 	= DC [Int]								deriving (Show)
data Message 	= MSG (Dancer, Int)
data Mailbox 	= MB (MVar Message)
data Dancer 	= Follower Int Mailbox DanceCard | 
				  Leader Int [Dancer] Mailbox DanceCard

-- must have the dances defined, 8 of them
-- will have to write this in 'main' too
dances = ["Waltz","Tango","Foxtrot","Quickstep",
		  "Rumba","Samba","Cha Cha","Jive"]
 

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
				then putMVar l_mv (MSG ((Follower id (MB mv) card), -1))	-- no
			else if (dancedWithID card l_id 0) > 2 
				then putMVar l_mv (MSG ((Follower id (MB mv) card), -1))	-- no
			else do
				let marked_card = markCard card dance l_id 
				putMVar l_mv (MSG ((Follower id (MB mv) marked_card), dance))		-- yes
			-- followers don't stop for now. so loop
			-- also still need to mark card, redo loop
			loop 
			-- where
			--	respond_no 	= (MSG ((Leader id followers (MB l_mv) card)), -1)
			--	respond_yes = (MSG ((Leader id followers (MB l_mv) card)), id)

-- leader :: Int -> [Dancer] -> Mailbox -> DanceCard -> IO ()
-- might have to do leader with a state?

leaderH :: Int -> [Dancer] -> Mailbox -> DanceCard -> Int -> IO ()
leaderH id followers (MB mv) card current_dance
	| current_dance > 8 	= return ()
	| otherwise 			= do
		-- pick a dancer
		index <- randomRIO (0,7)
		let (Follower f_id (MB f_mv) f_card) = followers !! index
		-- ask them to dance
		putMVar f_mv (MSG ((Leader id followers (MB mv) card), current_dance))
		-- get a response, if yes, mark card and increment current_dance
		(MSG (dancer, response)) <- takeMVar mv
		if response == (-1) then leaderH id followers (MB mv) card current_dance -- no
		else do
			let markedCard = markCard card current_dance index
			leaderH id followers (MB mv) markedCard (current_dance+1)			-- yes
		-- repeat

dance :: Dancer -> IO ()
dance (Follower id mailbox card) 			= follower id mailbox card
dance (Leader id followers mailbox card) 	= leaderH id followers mailbox card 0

-- ==============================================================
-- startDance
	-- start m followers   so  forkIO $ follower...
	-- start n leaders		   forkIO $ leader....
	-- wait for them to dance it out
	-- when leaders are done, end it all
	-- print results

-- data Logger = Logger (MVar LogCommand)
-- data LogCommand = Message String | Stop (MVar ())

	-- logger :: Logger -> IO ()
	-- logger (Logger m) = loop
	--  where
	--   loop = do
	--    cmd <- takeMVar m
	--    case cmd of
	--      Message msg -> do
	--        putStrLn msg
	--        loop
	--      Stop s -> do
	--        putStrLn "logger: stop"
	--        putMVar s ()

		






