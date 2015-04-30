import Control.Concurrent.MVar

-- leaders, followers, and dance numbers are all indexed from zero in this
-- program

-- sets the value at the spot in the list marked by ind (index) to val
setValue :: [Int] -> Int -> Int -> [Int]
setValue list ind val = (fst parts) ++ [val] ++ (tail (snd parts))
	where parts = splitAt ind list

-- replaces the first zero found with a one
-- ind must be given as zero
addAOne :: [Int] -> Int -> [Int]
addAOne list ind
	| length list == ind = list
	| list !! ind == 0   = setValue list ind 1
	| otherwise          = addAOne list $ succ ind

-- finds the index of the first instance of val, or -1 if there are none
-- ans must be given as zero
findInstance :: [Int] -> Int -> Int -> Int
findInstance list val ans
	| null list        = (-1)
	| head list == val = ans
	| otherwise        = findInstance (tail list) val (succ ans)

-- sets all values of a list to zero
reset :: [Int] -> [Int]
reset list = [x*0 | x <- [1..length list]]

-- followersAsked must be given as a list of all zeros with length equal to
-- the number of followers
-- dances must be given as a length 8 list with all values initialized to -2
leader :: Int -> [MVar Int] -> [Int] -> [Int] -> [Int]
leader id followers dances followersAsked
	| not $ elem 0 followersAsked = if ((findInstance dances (-2) 0) /= (-1))
					then leader id followers (setValue dances (findInstance dances (-2) 0) (-1)) (reset followersAsked)
					else dances
	| otherwise = putMVar (followers !! (findInstance followers 0 0)) id
