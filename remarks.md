
1. Zanimljivo je koji je odnos Service vs BoundService. Svaki service može biti jedno od toga ili oboje. Mi smo do sada koristili oboje. Ak je samo Service onda se ne može bindati i moraš ga eksplicitno startati. Ako je samo BoundService onda se sam starta kad se netko binda na njega i sam se ubije kad više nema nikoga Bindanog. Ako je oboje onda je mix tih ponašanja.

2. I Activity i Service bi mogli imati svoj ViewModel, no problem je što ga ne mogu shareati jer bi owner tog ViewModela bio različit. Android interno hendla ViewModel lifecycle tako da je `ViewModel(owner)` živ i nepromijenjen sve dok je owner živ.

3. Service je implementation detail i glupo je da se Activity binda na Service SAMO zato do bi mogao doći do par LiveData polja, to je **najružniji coupling u cijeloj priči** - iako bi ovaj pristup radio i tad bi nam Service bio ujedno i ViewModel i neka vrsta Repositorya. Ovaj pristup nas dosta limitira jer nam veže Android framework specific stvari (Service) za poslovnu logiku i podatke.

4. Na osnovu razgovora i svega što sam saznao od Weksa, te par novih stvari koje sam proučio predlažem:
    1. Ne koristimo uopće BoundService i da decouplamo Activity i Service
    2. Uvedemo shared model (AppModel) na razini aplikacije (koji onda možda vrši ulogu i repositorya?) koji exposea implementation independent facade događaja i valuea aplikacije. To je zapravo neki set LiveData objekata koje možeš slušati i na koje možeš pisati.
    3. Implementiramo AppModel kao singleton i napravimo service locator u App klasi (`App.getAppModel()`). Singleton nije najsretniji, ispravnije bi bilo da koristimo dependency injection. Ja na svim projektima intenzivno koristim DI i skoro ništa bez toga ne radim, no ovdje bih glasao za NE/NE JOŠ, jer ionako smo zaspammani s nepoznanicama.
    4. Service piše po tom shared modelu. Activity ga consumea. Service i Activity ne znaju jedno za drugo. Activity može i ne koristiti direktno taj AppModel, već imati i neki svoj klasični ViewModel između (što je u ovom scenariju moguće, a prije takav ViewModel nije mogao do Servicea), ali nisam siguran koji scenarij to zahtjeva.
    5. Mislim da bi taj AppModel trebao interno i hendlati svu logiku koja nije direktno povezana s komunikacijskom problematikom sa serverom niti s UIem - on je nekakav business layer.

Dobijemo jednostavniji kod i manji coupling.

Isprobao sam ovakav dizajn s par trivijalnih scenarija i trenutno mi izgleda OK. Sigurno sam štošta nafulavao jer sam u skroz novom/nepoznatom okruženju.

Kod je na https://github.com/sstublic/Experimental.WearOSHelloWorld

Pogledajte, popljujte, unaprijedite. Možemo se čuti ako imamo što za komentirati.
