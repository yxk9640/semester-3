# The Automobile class holds general data # about an automobile in inventory.
class Automobile:
    # The __init__method accepts arguments for the make, model, mileage, and price. 
    #It initializes the data attributes with these values.
    def __init__(self, make, model, mileage, price): 
        self.__make = make
        self.__model = model 
        self.__mileage = mileage 
        self.__price = price
        
    # The following methods are mutators for the class's data attributes.
    def set_make(self, make): 
        self.__make = make
    def set_model(self, model): 
        self.__model = model
    def set_mileage(self, mileage): 
        self.__mileage = mileage
    def set_price(self, price): 
        self.__price = price
        
    # The following methods are the accessors for the class's data attributes.
    def get_make(self): 
        return self.__make
    def get_model(self): 
        return self.__model
    def get_mileage(self): 
        return self.__mileage
    def get_price(self): 
        return self.__price
    
class Car(Automobile):
   
    # The __init__ method accepts arguments for the car's make, model, mileage, price, and doors.
    def __init__(self, make, model, mileage, price, doors):
        
        # Call the superclass's __init__ method and pass the required arguments.
        Automobile.__init__(self, make, model, mileage, price)
        # Initialize the __doors attribute. 
        self.__doors = doors
        
    # The set_doors method is the mutator for the __doors attribute.
    def set_doors(self, doors):
            self.__doors = doors
            
    # The get_doors method is the accessor for the __doors attribute.
    def get_doors(self):
            return self.__doors
