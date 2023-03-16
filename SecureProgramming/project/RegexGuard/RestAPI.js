// SQL errors shuld be handled write config files and write report

function restAPI() {

    const sqlite3 = require('sqlite3');
    const db = new sqlite3.Database('PhoneBook.db');
    const express = require('express');
    const app = express();
    //middleware
    app.use(express.json());



    const port = process.env.PORT || 5001;

    app.listen(port, () => {
        console.log(`App running at http://localhost:${port}/PhoneBook/list`); //List
    });


    app.get('/PhoneBook/list', function(req, res) {
        try {
            //  Return list of userd from phonebook db 
            db.all(`SELECT * FROM PhoneBook`, (err, row) => {
                if (err) {
                    res.status(400).json({ "error": err.message });
                    return;
                }
                res.status(200).json(row);
                console.log(req.params.lastname)
            });

        } catch (error) {
            console.log(error);
        }


    });


    // Input validation and regex validation
    app.post('/PhoneBook/add', function(req, res) {
        var body = req.body;
        const patternNumber = new RegExp(/(^[+]*[-\s(\s\.0-9]{0,2}[-\s\.0-9]{1,3}[\s)\s]{0,3}[-\s\.0-9]*$)|([0-9]{5,})/);
        const Namepattern = new RegExp(/^([a-z]|[A-Z]|[\s]|,|[?!.*']|-)*$/);
        var number = patternNumber.test(req.body.number) == true ? req.body.number : " ";
        // var fullName = req.body.firstname + req.body.lastname;
        var name = Namepattern.test(name) == true ? name : " ";


        if (number === " " || name === " ") {
            res.status(400).send('Either name and number or both are invalid. Please enter a valid input');
        } else {
            db.run(`INSERT INTO PhoneBook (name,number) VALUES (?, ?)`,
                //apply regex pattern for first name and last name and number
                [body.name, body.number], (err, row) => {
                    if (err) {
                        res.status(400).json({ "error": err.message });
                        return;
                    }
                    res.status(200).json({ "success": true });

                });
            return;
        }
    });

    app.put('/PhoneBook/deleteByName', function(req, res) {
        db.run(`DELETE FROM PhoneBook WHERE name = ?`, req.body.name,
            (err, row) => {
                if (err) {
                    res.status(404).json({ "error": "No user records found" });
                    return;
                }
                res.status(200).json({
                    status: ' deleted ,' + req.body.name
                });
            });
        return;
    });

    app.put('/PhoneBook/deleteByNumber', function(req, res) {
        db.run('SELECT COUNT(*) FROM PhoneBook WHERE number = ?', req.body.number, (err, row) => {
            if (row === undefined) {
                res.status(404).json({ "error": "No user records found" });
            } else {
                db.run(`DELETE FROM PhoneBook WHERE number = ?`, req.body.number,
                    (err, row) => {
                        if (req.body.number === undefined) {
                            res.status(404).json({ "error": "No user records found" });
                            return;
                        }
                        res.status(200).json({
                            status: ' deleted ,' + req.body.number
                        });
                    });
            }

            // console.log(req.body);
        })
    });

}
module.exports = restAPI;