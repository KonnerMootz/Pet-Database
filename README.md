Pet Database Assignment

Author: Konner Mootz
Class: CSC 422
Professor: Gregory Silver


Assignment Info:

This assignment will give you practice of using version control such as git to manage changes of your code and to give you experience of incremental development.
Create a basic database program for managing information (name and age) about pets.  The database allows the user to add pet information to the database, remove pet information, updating pet information, and searching for pets by name or by age.  You can assume the user only input pet names consisting of a single word.
You will build this program incrementally.  For each increment, you will create a release that the user can download and run.  You will use git and GitHub to track the changes and to create releases.  You must use appropriate design and make use of Object-Oriented Design.  See milestones.


Issues experienced during development:

* Scanner skipping user input
    * Cause: nextLine was taking user's "enter" key as the entry
    * Resolution: Catch the empty line with another nextLine call.

* Handling errors when different type of data is entered
    * Cause: User enters letter instead of number, etc
    * Resolution: Try Catch blocks with exception handling

* How to re-populate ArrayList from txt file
    * Cause: Needed way to get up to date info from text file
    * Resolution: Create File Management class > readDatabaseFile method which returns txt file data as ArrayList

* How to separate string, only getting name and age (and providing error when more or less than that is entered)
    * Cause: Allowing incorrect user entry
    * Resolution: regex pattern matching for specific values

* Program not accepting done command when case incorrect
    * User types done is case that isn't specified
    * Set user input as lower case and compare that to string: "done"

* Program not using correct .group() for pattern matching
    * Cause: .group() seems to start at one and not zero
    * Resolution: Update group numbering to one and three
    * Ex: String name = matchPetInfo.group(1); String petAge = matchPetInfo.group(3);

* Error message stating you need to pass objects of similar type
    * Cause: Was trying to pass String ArrayList
    * Resolution: Change ArrayList type to Pet Object

* Program accepting Pet ID one higher than available
    * Cause: Using ArrayList .size() will be one higher than requested since ArrayList is index zero
    * Resolution: Subtract one from size when comparing.  Ex:  if (petID > pets.size() - 1)

* Program not returning pets that match first couple of letters
    * Ex: Searching for "dog" does not return "doggy"
    * Cause: Was using .equals() comparison
    * Resolution: Use .contains() instead
