db = db.getSiblingDB("medicalrecorddb");
db.dummyCollection.insert({ name: "empty-initial-data" });
db.createUser({
    user: "medicalrecord-user",
    pwd: "1234",
    roles: [
      {
        role: 'readWrite', 
        db: 'medicalrecorddb'
      },
    ],
  });

  