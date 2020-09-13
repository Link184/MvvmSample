---
MVVM sample project

1. The business logic is placed in a separate gradle module to be reused wherever we want. 

    With Koltin Multiplatform that module can be compiled as jar for jvm, as binary for desktop or ios, as npm package or minified js code for browsers.
    
    Unit tests stay [HERE](https://github.com/Link184/MvvmSample/tree/master/core/src/jvmTest)

2. Android implementation contains only presentation and rendering logic

    [THIS](https://github.com/Link184/ArchitectureMVVM) MVVM framework was used.
    
    [THIS](https://github.com/Link184/KidAdapter) library was used for comfortable work with recycler view adapter.
