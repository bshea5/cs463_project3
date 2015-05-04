import Control.Concurrent

data DanceCard 	= DC [Int]								deriving (Show)
data Message 	= MSG (Dancer, Int)
data Mailbox 	= MB (MVar Message)
data Dancer 	= Follower Int Mailbox DanceCard | 
				  Leader Int [Dancer] Mailbox DanceCard

-- must have the dances defined, 8 of them
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

-- follower :: Int -> Mailbox -> DanceCard -> IO ()
-- follower id (MB mv) card = loop
-- where
--	loop = do
--		(MSG (dancer, dance)) <- takeMVar mv
--		-- check contents of message
--		-- send message back to leader

-- dance :: Dancer -> IO ()
-- dance (Follower id mailbox card) 		= follower id mailbox card
-- dance (Leader id followers mailbox card) = leader id followers mailbox card

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

-- mailing :: Mailbox -> IO ()
-- mailing (MB mv) = loop 
-- where
--	loop = do
--		content <- takeMVar mv
		






