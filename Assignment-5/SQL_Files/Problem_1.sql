CREATE TABLE Location (
    Location_Id int NOT NULL PRIMARY KEY,
    Location_Name varchar(255) NOT NULL
);

CREATE TABLE Travelling_Show (
    Show_Id int NOT NULL PRIMARY KEY,
    City varchar(255) NOT NULL,
    Start_Date varchar(255) NOT NULL,
    End_Date varchar(255) NOT NULL
);

CREATE TABLE Gallery (
    Gallery_Id int NOT NULL PRIMARY KEY,
    Gallery_Name varchar(255) NOT NULL,
    Gallery_City varchar(255) NOT NULL
);

CREATE TABLE Museum (
    Museum_Id int NOT NULL PRIMARY KEY,
    Location_Id int NOT NULL,
    Item_Code int NOT NULL,
    Show_Id int NOT NULL,
	FOREIGN KEY (Location_Id) REFERENCES Location(Location_Id),
    FOREIGN KEY (Show_Id) REFERENCES Travelling_Show(Show_Id)
);

CREATE TABLE Artist (
    Artist_Id int NOT NULL PRIMARY KEY,
    Artist_Name varchar(255) NOT NULL,
    Date_of_Birth varchar(255) NOT NULL,
    Date_of_Death varchar(255)
);

CREATE TABLE Art_Work (
    Item_Code int NOT NULL PRIMARY KEY,
    Title varchar(255) NOT NULL,
    Art_Type varchar(255) NOT NULL,
    Artist_Id int,
    Gallery_Id int NOT NULL,	
	FOREIGN KEY (Artist_Id) REFERENCES Artist (Artist_Id),
    FOREIGN KEY (Gallery_Id) REFERENCES Gallery(Gallery_Id)
);

CREATE TABLE Size (
    Item_Code int NOT NULL,
    Height int NOT NULL,
    Width int NOT NULL,
    Weight int NOT NULL,
    FOREIGN KEY (Item_Code) REFERENCES Art_Work(Item_Code)
);

ALTER TABLE Museum
ADD FOREIGN KEY (Item_Code) REFERENCES Art_Work(Item_Code);

