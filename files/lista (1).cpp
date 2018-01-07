#include <iostream>
#include <string>
#include "lista.h"


using namespace std;

Lista::Lista(){
	first=NULL;
}

Lista::~Lista(){
    Link *pomoc = first;
    while(pomoc != NULL){
        pomoc = first -> next;

        first = NULL;
        first = pomoc;
    }
}

void Lista::dodaj_osobe(string im, string naz, double wi)
{
	Link *nowy=new Link(im, naz, wi);
	if (first==NULL)
	{
		first=nowy;
	}else {
	Link *pomoc=first;
	while (pomoc->next != NULL)
	{
		pomoc=pomoc->next;
	}
	pomoc->next=nowy;
}

}

void Lista::wyswietl_liste()
{
	if (first==NULL)
	{
		cout<<"Brak elementów"<<endl;
	}else {
		Link *pomoc=first;
		while(pomoc->next != NULL)
		{
			cout<<"----------------------"<<endl;
			cout<<"Imie i nazwisko: "<<pomoc->wsk->imie<<" "<<pomoc->wsk->nazwisko<<endl;
			cout<<"Wiek: "<<pomoc->wsk->wiek<<" l."<<endl;
			cout<<"----------------------"<<endl;
			pomoc=pomoc->next;
		}
		cout<<"----------------------"<<endl;
		cout<<"Imie i nazwisko: "<<pomoc->wsk->imie<<" "<<pomoc->wsk->nazwisko<<endl;
		cout<<"Wiek: "<<pomoc->wsk->wiek<<" l."<<endl;
		cout<<"----------------------"<<endl;
	}
}

void Lista::usun_osobe(int nr)
{
	if (nr == 1)
	{
		Link *pomoc=first;
		first=pomoc->next;
		pomoc=NULL;
	} else {
        int licz = 2;
        Link *pomoc = first;
        Link *pomoc2 = first -> next;
        while (nr != licz){
            pomoc = pomoc -> next;
            pomoc2 = pomoc2->next;
            licz = licz +1;
            }

        pomoc-> next = pomoc2-> next;
        pomoc2 = NULL;
    }
}

void Lista::usun_wszystkie(){
Link *pomoc = first;
    while(pomoc != NULL){
        pomoc = first -> next;

        first = NULL;
        first = pomoc;
    }
}

int Lista::policz_nazwiska(string naz){
    int ile = 0;
    Link *pomoc = first;
    while(pomoc != NULL){
        if(pomoc->wsk->nazwisko == naz){
            ile = ile + 1;
        }
        pomoc = pomoc-> next;
    }
return ile;
}
DaneOsobowe * Lista :: operator [](int i)
{
   int licz=0;
   i=i-1;
   Link *pomoc = first;
       while (i!=licz)
       {
           pomoc=pomoc->next;
           licz=licz+1;
           if (pomoc->next==NULL)
           {
               cout<<"Z³y indeks";
               return first->wsk;
           }
       }
       return pomoc->wsk;
}

bool Lista::operator== (Lista druga){
    Link *pomoc = first;
    Link *pomoc2 = druga.first;
    int licznik1 = 0;
    int licznik2 = 0;
    while ( pomoc != NULL and pomoc2 != NULL){

        if(pomoc->wsk->nazwisko == pomoc2->wsk->nazwisko and pomoc->wsk->imie == pomoc2->wsk->imie and pomoc->wsk->wiek == pomoc2->wsk->wiek){
            pomoc = pomoc -> next;
            pomoc2 = pomoc2->next;
            licznik1++;
            licznik2++;
        }
    }
    if(pomoc == NULL){licznik1++;}
    if(pomoc2==NULL){licznik2++;}
if(licznik1==licznik2){return 1;}
else {return 0;}
}
