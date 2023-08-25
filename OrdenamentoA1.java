

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;



public class OrdenamentoA1 {
    
    

    public static void main(String[] args) throws FileNotFoundException, IOException {
       ArrayList<String> seq = new ArrayList<String>();
        
        String output = new String();
        String texto;
        Unsorted sort = new Unsorted();
        Unsorted pesq_uti= new Unsorted();
        Node [] max= new Node[100];
        Scanner s= new Scanner(System.in);
        int conta=0;
        while(!(texto=s.nextLine()).equals("TCHAU")){
            int existe=0;
            String [] texto_aux=texto.trim().split("[ \n]"); 
            
            int i=-1;
            if(texto_aux[0].equals("PALAVRAS")){ 
                conta=1;
                do{
                    i++; 
                    seq.add(s.nextLine().trim()); 
                    String[] seq_aux=seq.get(i).trim().split("[ ();\n,.]"); //seq_aux[0]->palavra ; seq_aux[1]->id user
                    if(!seq_aux[0].equals("FIM")){
                        existe=0;  
                        for(int k=0;k<sort.size();k++){  
                            if(sort.get(k).palavra.toLowerCase().compareTo(seq_aux[0].toLowerCase())==0){
                                existe=1;
                                sort.get(k).conta++; 
                                pesq_uti.get(k).conta++;
                               
                                if(sort.find(Integer.parseInt(seq_aux[1]),k)==1){
                                    sort.get(k).num[sort.get(k).indice]=Integer.parseInt(seq_aux[1]);
                                    sort.get(k).indice++;
                                    pesq_uti.get(k).num[pesq_uti.get(k).indice]=Integer.parseInt(seq_aux[1]);
                                    pesq_uti.get(k).indice++;
                                }
                            }
                            
                        }
                        if(existe==0){ 
                            Node nova=new Node(seq_aux[0],Integer.parseInt(seq_aux[1]));
                            sort.insert(nova);
                            pesq_uti.insert(nova);
                        }
 
                     
                    }
                }while(!seq.get(i).equals("FIM."));
                
                
                output=output+"GUARDADAS\n";
                MergeSort2(pesq_uti,pesq_uti.size());
                MergeSort(sort,sort.size()); 
            }
                
            else if (texto_aux[0].equals("PESQ_GLOBAL") && conta==1){
                Node [] lexical_order=new Node[50];
                int j=0;
                lexical_order[j]=sort.get(j);
                int aux=sort.size()-1;
                while(j<(aux)){
                    if(sort.get(j+1).conta==sort.get(j).conta){ 
                       lexical_order[j+1]=sort.get(j+1);
                       j++;
                       
                    }
                    else{
                        break;
                    }
                 
                }
               
                mergesort_alpha(lexical_order,j+1);
                for(int t=0;t<j+1;t++){
                    if(t!=j){
                        output=output+lexical_order[t].palavra.toUpperCase()+" ";
                    }
                    else{
                         output=output+lexical_order[t].palavra.toUpperCase()+"\n";
                    }
                }
                
            }    
            else if(texto_aux[0].equals("PESQ_UTILIZADORES")&& conta==1){
              int j=0;
              max[j]=pesq_uti.get(j);
              int aux=pesq_uti.size()-1;
           
             while(j<(aux)){
                    if(pesq_uti.get(j+1).indice==pesq_uti.get(j).indice){ // se a palavra for igual
                       max[j+1]=pesq_uti.get(j+1);
                       j++;
                    }
                    else{
                        break;
                    }
                }
                
                mergesort_alpha(max,j+1);
                for(int t=0;t<j+1;t++){
                    if(t!=j){
                        output=output+max[t].palavra.toUpperCase()+" ";
                    }
                    else{
                         output=output+max[t].palavra.toUpperCase()+"\n";
                    }

                }
        }
        
    }
        System.out.print(output);
    }
          

    private static void mergesort_alpha(Node[] lexical_order,int tam) {
        int metade=tam/2;
        if(tam<2)
            return;
        Node [] esquerda= new Node[metade];
        Node [] direita= new Node[tam-metade];
        for(int i=0;i<metade;i++){
            esquerda[i]=lexical_order[i];
        }     
        for(int j=metade;j<tam;j++){
              direita[j-metade]=lexical_order[j];
        }    
 
        mergesort_alpha(esquerda, metade);
        mergesort_alpha(direita, tam-metade);
        order_lexical(lexical_order,direita,esquerda);
    }

     private static void order_lexical(Node[] lexical_order, Node[] direita, Node[] esquerda) {
          int i=0,j=0,k=0;
         while (i < esquerda.length && j < direita.length) { 
            if(esquerda[i].palavra.compareTo(direita[j].palavra)<=0){
                lexical_order[k++]=esquerda[i++];   
            }
            else{
                 lexical_order[k++]=direita[j++];
                
            }
            
        }

         while (i < esquerda.length) {
             lexical_order[k++]=esquerda[i++];
          
        }
        while (j < direita.length) {
           lexical_order[k++]=direita[j++];
        }
            
    }
    
    
   
    
    
    
    private static void printarray(ArrayList<Node> unsorted) {
        for (int i=0;i<unsorted.size();i++){
            System.out.println("palavra no array: "+unsorted.get(i).palavra+" com contador: "+unsorted.get(i).conta);
        }
    }
    
    public static void MergeSort(Unsorted lista_princ,int tam){
        int metade=tam/2; 
        if(tam<2){
            return;
        }
        
       
        Unsorted array_esquerda=new Unsorted();
        Unsorted array_direita= new Unsorted ();
        
        for(int i=0;i<lista_princ.size();i++){
            if(i<metade){
                array_esquerda.insert(lista_princ.get(i));
            }
            else{
                array_direita.insert(lista_princ.get(i));
            }
        }
        MergeSort(array_esquerda,metade);
        MergeSort(array_direita,tam-metade);
        
        ordena_junta(lista_princ,array_direita,array_esquerda);

    }
    
    //ORDENA O PESQ_GLOBAL
    public static void ordena_junta(Unsorted lista_princ,Unsorted array_direita,Unsorted array_esquerda){
        int i=0,j=0,k=0;
        while (i < array_esquerda.size() && j < array_direita.size()) {  
            if(array_esquerda.get(i).conta>array_direita.get(j).conta){
                lista_princ.insertpos(lista_princ, k++, array_esquerda, i++);  
            }
            else{
                 lista_princ.insertpos(lista_princ, k++, array_direita, j++);
            }
            
        }
        //SE FOR impar
         while (i < array_esquerda.size()) {
          lista_princ.insertpos(lista_princ, k++, array_esquerda, i++);
        }
        while (j < array_direita.size()) {
            lista_princ.insertpos(lista_princ, k++, array_direita, j++);
        }
            
    }
    

    
    public static void MergeSort2(Unsorted lista_princ,int tam){
        int metade=tam/2; 
        //condicao de paragem
        if(tam<2){
            return;
        }
        
       
        Unsorted array_esquerda=new Unsorted();
        Unsorted array_direita= new Unsorted ();
        
        for(int i=0;i<lista_princ.size();i++){
            if(i<metade){
                array_esquerda.insert(lista_princ.get(i));
            }
            else{
                array_direita.insert(lista_princ.get(i));
            }
        }
        MergeSort2(array_esquerda,metade);
        MergeSort2(array_direita,tam-metade);
        
        ordena_junta_utili(lista_princ,array_direita,array_esquerda);

    }
    
   
    
    public static void ordena_junta_utili(Unsorted lista_princ,Unsorted array_direita,Unsorted array_esquerda){
        int i=0,j=0,k=0;
        while (i < array_esquerda.size() && j < array_direita.size()) { 
            if(array_esquerda.get(i).indice>array_direita.get(j).indice){
                lista_princ.insertpos(lista_princ, k++, array_esquerda, i++);   
            }
            else{
                 lista_princ.insertpos(lista_princ, k++, array_direita, j++);
            }
            
        }
        //SE FOR impar
         while (i < array_esquerda.size()) {
          lista_princ.insertpos(lista_princ, k++, array_esquerda, i++);
        }
        while (j < array_direita.size()) {
            lista_princ.insertpos(lista_princ, k++, array_direita, j++);
        }
            
    }
    

}
    
    


class Node{
    String palavra;
    int conta;
    int[] num=new int[100];
    int indice=0;
    Node(String palavra,int num){
        this.palavra=palavra;
        this.conta=1;
        this.num[indice]=num;
        indice++;
        
    }
}


class Unsorted { 
    Unsorted head=null;
    Node novo;
    Unsorted next;
    
    
    public void insert(Node novo) 
    { 
        Unsorted aux = new Unsorted();
        aux.novo=novo;
        aux.next=null;
        if (this.head == null) { 
            head=aux;
            head.next=null;
        } 
        else { 
            Unsorted last = this.head; 
            
            while (last.next!= null) { 
                last = last.next; 
  
            } 

            last.next=aux;
    
        } 

   
    }
    
    public void insertpos(Unsorted unsorted,int k,Unsorted muda,int pos){
        int aux=0;
        Unsorted auxi=head;
        Unsorted auxi2=auxi;
        if(aux==k){
            auxi2.novo=(muda.get(pos));
        }
        
        
        while(aux!=k){
            auxi2=auxi;
            auxi=auxi.next;
            aux++;
        }
        auxi2.next.novo=(muda.get(pos));
        
         

   
    }
    
       
    public int size(){
        int size=1;
        Unsorted last=this.head;
        if(this.head==null) return 0;
        while (last.next!= null) { 
                last = last.next; 
                size++;
            }
         return size;
    }
       
    public Node get(int k){
        int aux=0;
        Unsorted last=this.head;
        while (aux!=k) { 
                last = last.next; 
                aux++;

            }
        return last.novo;
    }
    
    public int find(int num,int pos){
        Node currNode = this.get(pos);
            for(int i=0;i<currNode.num.length;i++){
                if(num==currNode.num[i]){
                    return 0;
                }
            }
      
         return 1;
    }
    
    
    
       public  void printList() 
    { 
        Unsorted currNode = this.head; 
        while (currNode != null) { 
            System.out.println("curr "+currNode.novo.palavra+" COM CONT: "+currNode.novo.conta);
        
            currNode = currNode.next; 
            
        } 
    } 
    
       
    
 }
   