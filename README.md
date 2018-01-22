# BookSearch

This application comprises the 7th project of the course Android Basics Nanodegree by Udacity/Google. It consists of a UI that lets the user type a book´s subject. The app itself implements a network http request that connects to the Google Books API in order to retrieve the data of the book like author´s name, title, description and image preview. 

Book search field           |  Searching Android         |   Searching wearables         |
:-------------------------:|:-------------------------:|:-------------------------:|
![](https://cloud.githubusercontent.com/assets/23319417/23269598/c25ef2b2-f9b6-11e6-9281-0524dd538fe0.png)  |  ![](https://cloud.githubusercontent.com/assets/23319417/23269599/c284eada-f9b6-11e6-9b52-272cd4d1bf45.png) |  ![](https://cloud.githubusercontent.com/assets/23319417/23269600/c292c452-f9b6-11e6-9e8c-6747e4e670c3.png) |


In this project different topics are covered like : 

The main topics of this lesson were:
1. JSON parsing
2. HTTP network requests
3. Web Services and third party API´s / Consuming data from a third party library in real time.

The main methods and classes for implementing the HTTP request, as well as catching the error during the connection was very important in this lesson. 

The data is displayed in a List View using a BookAdapter for objects of type Book.
On the other hand, along the implementation an AsyncTask was used for the basic implementation, but further or most recent commits consisted on migrating from AsyncTask to AsyncLoader in order to have a better performance i.e. device rotation and so on. 

The UX design shows a progress bar while the connection is being made and when there´s no internet connection,as well as when there are not books found, an emptyView is set on the UI to let the user know that something is going wrong.

Edit : 
The View Holder patter simply is implemented on the adapter class.
https://developer.android.com/training/improving-layouts/smooth-scrolling.html
 
