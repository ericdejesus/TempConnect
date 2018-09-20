# About #
TempConnect is an Android Studio Application that uses Google sign in for  Authentication and Firebase for storage. Using Firebase's api,
csv files are uploaded to Firebase's cloud storage. When selected in the list, it'll download the csv file and plot it. The graph will either plot temperature
over time or humidity over time, which were randomly generated in MATLAB.

For our design we wanted it to go Signin->List of CSV -> (Graph the csv) or (upload csvs)


# Installing #
Due to how the GoogleSign in and Firebase Authentication works, if you would like to sign in or even access the database you have to register your SHA-1 certificate both in the project's Google console and Firebase console.
Therefore the code will be built but it'll close because Google/Firebase won't except the request. We have included the apk with the github and that can be placed instead.

We included the .apk for the project on the root level of the project directory. Just download it to your phone and click on it and it should install. It'll crash when uploading csv files but hit reload and it'll work as intended.
We included csv files for testing, it uses the first row name to determine whether to graph for humidity or temperature. Place those in your phone's "Download" folder.
