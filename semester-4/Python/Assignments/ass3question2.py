class Course:
    def __init__(self, code, name):
        self.code = code
        self.name = name
        self.enrolled_students = []

    def enroll_student(self, student):
        self.enrolled_students.append(student)

    def drop_student(self, student):
        self.enrolled_students.remove(student)

    def display_enrolled_students(self):
        for student in self.enrolled_students:
            print(student.name)

class Lecture(Course):
    def __init__(self, code, name, instructor):
        super().__init__(code, name)
        self.instructor = instructor

class Lab(Course):
    def __init__(self, code, name, ta):
        super().__init__(code, name)
        self.ta = ta

class Seminar(Course):
    def __init__(self, code, name, guest_speaker):
        super().__init__(code, name)
        self.guest_speaker = guest_speaker

class Student:
    def __init__(self, id, name):
        self.id = id
        self.name = name
        self.enrolled_courses = []

    def enroll_course(self, course):
        self.enrolled_courses.append(course)
        course.enroll_student(self)

    def drop_course(self, course):
        self.enrolled_courses.remove(course)
        course.drop_student(self)

    def display_enrolled_courses(self):
        for course in self.enrolled_courses:
            print(course.name)

class Undergraduate(Student):
    def __init__(self, id, name):
        super().__init__(id, name)

class Graduate(Student):
    def __init__(self, id, name):
        super().__init__(id, name)

lecture = Lecture("CS101", "Intro to Computer Science", "Prof. Smith")
lab = Lab("CS101L", "Intro to Computer Science Lab", "John Doe")
seminar = Seminar("CS201", "Advanced Topics in Computer Science", "Dr. Jane")

undergrad1 = Undergraduate(1, "Alice")
undergrad2 = Undergraduate(2, "Bob")
grad1 = Graduate(3, "Charlie")
grad2 = Graduate(4, "Dave")

lecture.enroll_student(undergrad1)
lecture.enroll_student(grad1)
lecture.enroll_student(grad2)

lab.enroll_student(undergrad1)
lab.enroll_student(undergrad2)

seminar.enroll_student(grad1)
seminar.enroll_student(grad2)

undergrad1.enroll_course(lecture)
undergrad1.enroll_course(lab)
undergrad2.enroll_course(lab)
grad1.enroll_course(lecture)
grad1.enroll_course(seminar)
grad2.enroll_course(lecture)
grad2.enroll_course(seminar)

undergrad1.drop_course(lab)

print("Enrolled students in CS101 Lecture:")
lecture.display_enrolled_students()

print("Enrolled courses for Alice:")
undergrad1.display_enrolled_courses()