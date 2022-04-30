[![Android CI](https://github.com/scnplt/Hipo/actions/workflows/android.yml/badge.svg)](https://github.com/scnplt/Hipo/actions/workflows/android.yml)

# Hipo

The project was given by Hipo for internship application. An application that reads the data in the given JSON file and displays it on the screen in the form of a list.

## Folder Structure

```
.
├── main
│   ├── AndroidManifest.xml
│   ├── java
│   │   └── dev.sertan.hipoproject
│   │       ├── App.kt #----------------------------> App class required to use Hilt
│   │       ├── data                                      
│   │       │   ├── model #-------------------------> model classes                       
│   │       │   ├── module #------------------------> for dependency injection with Hilt                                   
│   │       │   ├── repository                              
│   │       │   │   └── HipoRepository.kt #---------> responds to requests using different data sources            
│   │       │   └── source                               
│   │       │       ├── HipoService.kt #------------> pulls data from the internet using Retrofit             
│   │       │       └── LocalDataSource.kt #--------> stores and presents data with SharedPreferences            
│   │       ├── ui                                          
│   │       │   ├── MainActivity.kt                              
│   │       │   ├── MemberFragment.kt #-------------> shows user information              
│   │       │   └── home                                   
│   │       │       ├── HomeFragment.kt #-----------> lists users           
│   │       │       ├── HomeViewModel.kt #----------> to use data as live data and prevent data loss          
│   │       │       ├── MemberAdapter.kt #----------> for recyclerview     
│   │       │       ├── MemberDiffUtilCallback.kt #-> for recyclerview
│   │       │       └── MemberViewHolder.kt #-------> for representation of each view in list items
│   │       └── util
│   │           └── State.kt #----------------------> used to display the status of incoming data
│   └── res #---------------------------------------> xml files
└── test #------------------------------------------> unit tests
```

## Theme

- Colors: Default [Material Design 3](https://m3.material.io/) colors

## Used Technologies and Libraries

| Libraries and Technologies                                                       | Why?                                        |
|----------------------------------------------------------------------------------|---------------------------------------------|
| [Parcelize](https://developer.android.com/kotlin/parcelize)                      | Parcelable implementation generator         |
| [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) | Dependency injection                        |
| [Retrofit](https://square.github.io/retrofit/)                                   | HTTP client                                 |
| [Coroutines](https://developer.android.com/kotlin/coroutines)                    | Asynchronous programming                    |
| [Lifeycle KTX](https://developer.android.com/kotlin/ktx#lifecycle)               | To use Coroutines with the Lifecycle object |
| [Navigation](https://developer.android.com/guide/navigation)                     | To navigate between pages                   |
| [ViewBinding](https://developer.android.com/topic/libraries/view-binding)        | To automatically generated a binding class  |
| [Truth](https://truth.dev/)                                                      | For unit tests                              |

## License

```
MIT License

Copyright (c) 2021 Sertan Canpolat

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
