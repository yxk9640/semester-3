import sqlite3
from tkinter import *
from tkinter import ttk

root = Tk()

mygreen = "#d2ffd2"
myred = "#dd0202"

style = ttk.Style()

style.theme_create("yummy", parent="alt", settings={
    "TNotebook": {"configure": {"tabmargins": [2, 5, 2, 0]}},
    "TNotebook.Tab": {
        "configure": {"padding": [5, 1], "background": mygreen},
        "map": {"background": [("selected", myred)],
                "expand": [("selected", [1, 1, 1, 0])]}}})

style.theme_use("yummy")

root.title("Railway Reservation System")

root.geometry("1200x600")

tabControl = ttk.Notebook(master=root)
tab1 = ttk.Frame(tabControl)
tab2 = ttk.Frame(tabControl)
tab3 = ttk.Frame(tabControl)
tab4 = ttk.Frame(tabControl)
tab5 = ttk.Frame(tabControl)
tab6 = ttk.Frame(tabControl)

tabControl.add(tab1, text="Task 1")
tabControl.add(tab2, text="Task 2")
tabControl.add(tab3, text="Task 3")
tabControl.add(tab4, text="Task 4")
tabControl.add(tab5, text="Task 5")
tabControl.add(tab6, text="Task 6")
tabControl.pack(expand=1, fill="both")


# Text boxes for window
# f_name = Entry(tab1, width=30)
# f_name.grid(row=1, column=1, padx=20)
# f_name_label = Label(tab2, text="First Name: ")
# f_name_label.grid(row=1, column=0)


def TableView(frame, cols):
    listBox = ttk.Treeview(frame, selectmode=BROWSE, columns=cols, show='headings')
    for col in cols:
        listBox.heading(col, text=col)
        listBox.column(col, width=150)
    listBox.grid(row=1, column=0, columnspan=2)

    return listBox


def tab1Contents():
    def query():
        conn = sqlite3.connect("rrs.db")
        sql = conn.cursor()

        result = sql.execute(
            "SELECT t.number, t.name, b.ticket_category, b.reservation_status, b.date, s.day FROM Passenger p INNER JOIN Booking b ON p.ssn = b.passenger_ssn INNER JOIN Train t ON t.number = b.train_number INNER JOIN Train_status s ON s.date = b.date AND s.day = t.on_day WHERE p.first_name=? COLLATE NOCASE AND p.last_name=? COLLATE NOCASE;",
            (f_name.get(), l_name.get()))

        rows = result.fetchall()

        for item in table.get_children():
            table.delete(item)
        for v in rows:
            table.insert("", "end", values=v)

        Label(tab1, text="Rows returned: " + str(len(rows))).grid(row=5, column=0)

        conn.commit()
        conn.close()

    f_name = Entry(tab1, width=30)
    f_name.grid(row=2, column=1, padx=20, pady=20)
    f_name_label = Label(tab1, text="First Name: ")
    f_name_label.grid(row=2, column=0)

    l_name = Entry(tab1, width=30)
    l_name.grid(row=3, column=1, padx=20, pady=20)
    l_name_label = Label(tab1, text="Last Name: ")
    l_name_label.grid(row=3, column=0)

    run_query = Button(master=tab1, text="Get all trains the passenger is booked on", command=query)
    run_query.grid(row=4, column=1)

    table = TableView(tab1, ("Train Number", "Train Name", "Ticket Category", "Reservation Status", "Date", "Day"))


def tab2Contents():
    def query():
        conn = sqlite3.connect("rrs.db")
        sql = conn.cursor()

        result = sql.execute("SELECT first_name, last_name, ssn, address, age FROM Passenger INNER JOIN Booking b ON ssn=passenger_ssn INNER JOIN (SELECT date from Train_status where day = ? COLLATE NOCASE) s ON s.date = b.date WHERE reservation_status='Booked'", (day.get(), ))
        rows = result.fetchall()
        for item in table.get_children():
            table.delete(item)
        for v in rows:
            table.insert("", "end", values=v)

        Label(tab2, text="Rows returned: " + str(len(rows))).grid(row=5, column=0)

        conn.commit()
        conn.close()

    day = Entry(tab2, width=30)
    day.grid(row=3, column=0, padx=20, pady=20)
    day_label = Label(tab2, text="Day: ")
    day_label.grid(row=2, column=0)

    run_query = Button(master=tab2, text="Get all passengers with confirmed tickets on a Day", command=query)
    run_query.grid(row=4, column=0)

    table = TableView(tab2, ("First Name", "Last Name", "SSN", "Address", "Age"))


def tab3Contents():
    def query():
        conn = sqlite3.connect("rrs.db")
        sql = conn.cursor()

        result = sql.execute(
            "SELECT DISTINCT t.number, t.name, t.source, t.destination, p.first_name, p.address, b.ticket_category, b.reservation_status FROM Train t INNER JOIN Booking b ON b.train_number=t.number INNER JOIN Passenger p ON p.ssn = b.passenger_ssn WHERE p.age = ?;",
            (age.get(),))
        rows = result.fetchall()

        for item in table.get_children():
            table.delete(item)
        for v in rows:
            table.insert("", "end", values=v)

        Label(tab3, text="Rows returned: " + str(len(rows))).grid(row=5, column=0)
        conn.commit()
        conn.close()

    age = Entry(tab3, width=30)
    age.grid(row=3, column=0, padx=20, pady=20)
    age_label = Label(tab3, text="Age of passenger (50 to 60): ")
    age_label.grid(row=2, column=0)

    run_query = Button(master=tab3, text="Get details", command=query)
    run_query.grid(row=4, column=0)

    table = TableView(tab3, (
    "Train Number", "Train Name", "Source", "Destination", "Passenger Name", "Passenger Address", "Ticket Category",
    "Booking Status"))


def tab4Contents():
    def query():
        conn = sqlite3.connect("rrs.db")
        sql = conn.cursor()
        result = sql.execute(
            "SELECT DISTINCT number, name, c FROM Train LEFT JOIN (SELECT DISTINCT train_number, count(*) as c from Booking GROUP BY train_number) t ON t.train_number=Train.number;")
        rows = result.fetchall()

        for item in table.get_children():
            table.delete(item)
        for v in rows:
            table.insert("", "end", values=v)

        Label(tab4, text="Rows returned: " + str(len(rows))).grid(row=3, column=1)
        conn.commit()
        conn.close()

    run_query = Button(master=tab4, text="Get all train names and count of passengers", padx=20, command=query)
    run_query.grid(row=2, column=1)

    table = TableView(tab4, ("Train Number", "Train Name", "No. of passengers"))


def tab5Contents():
    def query():
        conn = sqlite3.connect("rrs.db")
        sql = conn.cursor()

        result = sql.execute(
            "SELECT DISTINCT date, first_name, last_name, address, age FROM Passenger INNER JOIN Booking ON passenger_ssn=ssn INNER JOIN Train ON number=train_number WHERE reservation_status='Booked' AND name=? COLLATE NOCASE",
            (T_name.get(),))
        rows = result.fetchall()

        for item in table.get_children():
            table.delete(item)

        for v in rows:
            table.insert("", "end", values=v)

        Label(tab5, text="Rows returned: " + str(len(rows))).grid(row=5, column=0)
        conn.commit()
        conn.close()

    T_name = Entry(tab5, width=30)
    T_name.grid(row=3, column=0, padx=20, pady=20)
    T_name_label = Label(tab5, text="Train Name: ")
    T_name_label.grid(row=2, column=0)

    run_query = Button(master=tab5, text="Get all the passengers with confirmed status", command=query)
    run_query.grid(row=4, column=0)

    table = TableView(tab5, ("Date", "First Name", "Last Name", "Address", "Age"))

current_rows = []
def tab6Contents():

    def reloadTable():
        conn = sqlite3.connect("rrs.db")
        sql = conn.cursor()
        result = sql.execute("SELECT DISTINCT name, number, ssn, date, first_name, last_name, ticket_category, reservation_status FROM Passenger INNER JOIN Booking ON passenger_ssn=ssn INNER JOIN Train ON number=train_number ORDER BY reservation_status")
        rows = result.fetchall()
        global current_rows
        current_rows = rows
        for item in table.get_children():
            table.delete(item)
        
        for v in rows:
            table.insert("", "end", values=v)

        Label(tab6, text="Rows returned: " + str(len(rows))).grid(row=7, column=0)

        conn.commit()
        conn.close()

    def removeRecord():
        conn = sqlite3.connect("rrs.db")
        sql = conn.cursor()

        user_deleted_info = None
        for i in current_rows:
            if str(ssn.get()) == str(i[2]) and str(train_number.get()) == str(i[1]) and str(date.get()) == str(i[3]):
                user_deleted_info = i
                break


        result = sql.execute("DELETE FROM Booking WHERE passenger_ssn = ? AND train_number = ? AND date = ?", (ssn.get(), train_number.get(), date.get()))
        # Row has been deleted
        if result.rowcount == 1:
            result = sql.execute("SELECT passenger_ssn, ticket_category FROM Booking WHERE train_number = ? AND date = ? AND reservation_status='WaitList'", (train_number.get(), date.get()))
            tempRows = result.fetchall()
            # There are rows with waiting
            if len(tempRows) > 0:
                # print("Update SSN: ", tempRows[0][0])
                Label(tab6, text="Updated to Booked status of User SSN: " + str(tempRows[0][0])).grid(row=8, column=0)
                sql.execute("UPDATE Booking SET reservation_status='Booked' WHERE passenger_ssn = ? AND ticket_category = ?", (tempRows[0][0], user_deleted_info[6]))
            else:
                Label(tab6, text="No user is in waiting list to add to Booked").grid(row=8, column=0)
                deleted_category = user_deleted_info[6]
                if deleted_category == 'General':
                    currentSeats = sql.execute("SELECT seats_occupied_general FROM Train_status WHERE train_number = ? AND date = ?", (train_number.get(), date.get())).fetchall()[0][0]
                    sql.execute("UPDATE Train_status SET seats_occupied_general = ? WHERE train_number = ? AND date = ?", (currentSeats - 1, train_number.get(), date.get()))
                else:
                    currentSeats = sql.execute("SELECT seats_occupied_premium FROM Train_status WHERE train_number = ? AND date = ?", (train_number.get(), date.get())).fetchall()[0][0]
                    sql.execute("UPDATE Train_status SET seats_occupied_premium = ? WHERE train_number = ? AND date = ?",(currentSeats - 1, train_number.get(), date.get()))
                # No rows are updated
                # Try to update the count in Train_status
        conn.commit()
        conn.close()
        reloadTable()




    ssn = Entry(tab6, width=30)
    ssn.grid(row=2, column=1, padx=20, pady=20)
    ssn_label = Label(tab6, text="SSN: ")
    ssn_label.grid(row=2, column=0)

    train_number = Entry(tab6, width=30)
    train_number.grid(row=3, column=1, padx=20, pady=20)
    train_number_label = Label(tab6, text="Train Number: ")
    train_number_label.grid(row=3, column=0)

    date = Entry(tab6, width=30)
    date.grid(row=4, column=1, padx=20, pady=20)
    date_label = Label(tab6, text="Date: ")
    date_label.grid(row=4, column=0)

    run_query = Button(master=tab6, text="Cancel Ticket", command=removeRecord)
    run_query.grid(row=5, column=1)
    reload_query = Button(master=tab6, text="Reload Table", command=reloadTable)
    reload_query.grid(row=6, column=1)

    table = TableView(tab6, (
        "Train Name", "Train Number", "SSN", "Date", "First Name", "Last Name", "Ticket Category",
        "Booking Status"))

    reloadTable()


tab1Contents()
tab2Contents()
tab3Contents()
tab4Contents()
tab5Contents()
tab6Contents()

root.mainloop()
