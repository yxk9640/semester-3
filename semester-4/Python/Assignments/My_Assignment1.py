# Write your code over here.
print("Question 2 part 1")
digit = int(input("Enter a digit input value between 0 and 9: "))

# while digit > 9:
#     print("Error ")
#     digit = int(input("Enter a digit input value between 0 and 9: "))

if digit == 0:
    print("You entered zero.")
elif digit == 1:
    print("You entered One.")
elif digit == 2:
    print("You entered Two.")
elif digit == 3:
    print("You entered Three.")
elif digit == 4:
    print("You entered Four.")
elif digit == 5:
    print("You entered Five.")
elif digit == 6:
    print("You entered Six.")
elif digit == 7:
    print("You entered Seven.")
elif digit == 8:
    print("You entered Eight.")
elif digit == 9:
    print("You entered Nine.")
else:
    print("Enter number out of range")


print()

print("Question 2 part 2")
digit = int(input("Enter a digit input value between 0 and 9: "))

while digit > 9 or digit != -1:
    if digit == 0:
        print("You entered zero.")
    elif digit == 1:
        print("You entered One.")
    elif digit == 2:
        print("You entered Two.")
    elif digit == 3:
        print("You entered Three.")
    elif digit == 4:
        print("You entered Four.")
    elif digit == 5:
        print("You entered Five.")
    elif digit == 6:
        print("You entered Six.")
    elif digit == 7:
        print("You entered Seven.")
    elif digit == 8:
        print("You entered Eight.")
    elif digit == 9:
        print("You entered Nine.")
    else:
        print("Enter number out of range")
    
    print("Enter -1 to stop or other input to continue ")
    digit = int(input("Enter a digit input value between 0 and 9: "))
