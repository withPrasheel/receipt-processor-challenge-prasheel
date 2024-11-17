1) Since the counter has to generate a Hash like value, I am not using BigInteger to generate the value which can hold 10 Mil digits based on the processor used which is future proof for the application.

2) I am creating a Hash like value as given in the requirements.

3) Since there can be multiple get operations on the same hash, I am precalculating the points value and storing it with the key as ID.

4) I have added a global validation so if there is any issue when running the system it would throw an exception