//profile.pdb

CREATE TABLE IF NOT EXISTS TNORME (
	id INTEGER PRIMARY KEY AUTOINCREMENT, 
	nname VARCHAR(32) NOT NULL, 
	code VARCHAR(3)
);

CREATE TABLE IF NOT EXISTS TFAMILY (
	id INTEGER PRIMARY KEY AUTOINCREMENT, 
	fname VARCHAR(12) NOT NULL, 
	normeid INTEGER NOT NULL, 
	imageid INTEGER NOT NULL,
	drawerid SMALLINT DEFAULT (0),
	FOREIGN KEY(normeid) REFERENCES TNORME(id)
);

CREATE TABLE IF NOT EXISTS TIMAGE (
	id INTEGER PRIMARY KEY AUTOINCREMENT, 
	imagedata BLOB NOT NULL
);


CREATE TABLE IF NOT EXISTS TPROFILES (
	id INTEGER PRIMARY KEY AUTOINCREMENT, 
	normeid INTEGER NOT NULL,
	familyid INTEGER NOT NULL, 
	profilename VARCHAR(32) NOT NULL,
	characteristic TEXT NOT NULL,
	FOREIGN KEY(normeid) REFERENCES TNORME(id)	
	FOREIGN KEY(familyid) REFERENCES TFAMILY(id)	
);