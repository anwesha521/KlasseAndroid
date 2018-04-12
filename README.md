# KlasseAndroid
## The purpose of this app 

To facilitate learning in the classroom. It has two different modes - one for students and one for instructors. Both versions will include 5 main features - feedback, chatroom, quizzes, analytics and announcements. This app also includes a custom Notification manager that notifies the student and Instructor if an announcement or quiz has been posted.

### **Feedback**
Allows instructors to upload slides for each week for different classes as well as view anonymous feedback submitted by the students. For students they can view and download the slides uploaded by instructors and also submit feedback for different pages.

### **Announcements**
Allows instructors to post and view/delete their announcements for each class. Once an announcement is posted all students enrolled in that class will get a push notification with the contents of the announcement and they can then go onto view all announcements in a check-list layout where they can check an announcement once completed/acknowledged. 

### **Quiz**
Allows instrcutors to create and edit quizzes to be posted to all students enrolled in that class. Quizzes can be either MCQ or question-answer format. Both types have an autograding feature whereby grades can be calculated as soon as students submit answers. (For question-answer format an answer is graded correct if it has all the keywords that the instructor has set to be correct.) Once a quiz is posted all students enrolled in that class get a push notification regarding the quiz starting. Once a student takes a quiz they cannot take it again and their grades are recorded onto a central database.

### **Chatroom**
Allows both students and instructors to discuss ideas pertaining to the class. Students can post questions which can be replied to by other students. Instructors have the ability to verify the best answer.
The messages are colour coded for ease of viewing:
Blue: Question
Green: Reply
Yellow: Verified answer

### **Analytics**
Both instructors and students will be greeted by an analytics page after logging in. For instructors they can see the average performance of all their students throughout the term in the form of a bar graph that displays average grade percentage vs week. For students they can also see their performance in the form of a line graph of average percentage vs week. It will also show their performance in each individual subject throughout the term.

## Database and backend

We used Volley a framework that we used to query our MySql database. Volley is a framework on top of android framework. With Volley, you don’t need to write code in an Async task for accessing the network as this is managed by volley itself. Thus, with the exception of Chatroom (which uses Firebase) and push notifications (which uses FCM), all our features use volley request to POST and GET request to our database.

## Unit Testing

For unit testing, we used Robolectric which uses JVM framework which is much faster and efficient than Android Test framework. It does not require an emulator so you can test some of the project's non UI parts without requiring an emulator or device. We can also use shadow objects to mock an android object. With these shadow counterparts of Android Object, you can do internal checking or call special methods. Thus, it was best suited for our unit testing purpose. We could even test our volley requests with Robolectric.

