const sqlite3 = require('sqlite3').verbose();

const dbConnect = connect(sqlite3);
// const dbquery = processDatabase(dbConnect);
// close(dbConnect);

// Initiate a connection
function connect() {

    let db = new sqlite3.Database('./PhoneBook.db', sqlite3.OPEN_READWRITE, (err) => {
        if (err) {
            return console.error(err);
        }
        console.log('Connection established')

    });
    return db;
}

//process the database
function processDatabase(db) {
    db.serialize(() => {
        db.all(`SELECT * FROM PhoneBook`, (err, row) => {
            if (err) {
                return console.error(err);
            }
            console.log(row);
        });

    });

    return db;
}

//close a connection
function close(db) {

    db.close((err) => {
        if (err) {
            return console.error(err.message);

        }
        console.log('Connection closed');

    });
};

module.exports = connect;