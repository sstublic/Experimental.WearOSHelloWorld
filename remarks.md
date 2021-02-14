
1. Zanimljivo svc vs bound svc
2. I Activity i Service bi mogli imati svoj ViewModel, no problem je što ga ne mogu shareati jer bi owner tog ViewModela bio različit
3. Service je implementation detail i glupo je da se Activity binda na Service SAMO zato do bi mogao doći do par LiveData polja, to je najružniji coupling u cijeloj priči - iako bi ovaj pristup radio i tad bi nam Service bio ujedno i ViewModel i neka vrsta Repositorya

3. Ok je da svc bude viewmodel, no kako će se riješiti user akcije koje moraju otići na server? one trebaju biti asinkrone, tj. trebali bi 'post' nešto na service i što onda? vjerojatno bi bilo najbolje da ima 0 business logike u UIu. Pada mi na pamet neki separation: Activity <--> AppModel <--> HubConnection, gdje je veza AppModel/HubConnection direktna (tj. AppModel instancira HubConnection, čisto da kod koji hendla business logiku ne bude pomiješan s komunikacijskim kodom)




