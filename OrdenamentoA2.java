/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ordenamentoa2;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 *
 * @author bruno
 */
public class OrdenamentoA2 {

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
                           //se a palavra já existir no array
                            
                            if(sort.get(k).palavra.toLowerCase().compareTo(seq_aux[0].toLowerCase())==0){
                                existe=1;
                                sort.get(k).conta++; 
                                pesq_uti.get(k).conta++;
                                //vamos ao array num, no indice correspondente e adicionamos o novo num
                               
                                //se vamos ver se o numero ja existe nessa palavra
                                if(sort.find(Integer.parseInt(seq_aux[1]),k)==1){
                                    sort.get(k).num[sort.get(k).indice]=Integer.parseInt(seq_aux[1]);
                                    sort.get(k).indice++;
                                    pesq_uti.get(k).num[pesq_uti.get(k).indice]=Integer.parseInt(seq_aux[1]);
                                    pesq_uti.get(k).indice++;
                                }
                                break;
                            }
                            
                        }
                        if(existe==0){ //se a palavra nao existe ainda vai colocar no array
                            Node nova=new Node(seq_aux[0],Integer.parseInt(seq_aux[1]));
                            Node nova1=new Node(seq_aux[0],Integer.parseInt(seq_aux[1]));
                            sort.insert(nova);
                            pesq_uti.insert(nova1);
                        }
 
                     
                    }
                }while(!seq.get(i).equals("FIM."));
                
                
                output=output+"GUARDADO.\n";
               
                radixsort2(pesq_uti, pesq_uti.size());
                radixsort(sort,sort.size());

            }
                
            else if (texto_aux[0].equals("PESQ_GLOBAL") && conta==1){
  
                Node [] lexical_order=new Node[50];
                int j=0;
                lexical_order[j]=sort.get(j);
                int aux=sort.size()-1;
                while(j<(aux)){
                    if(sort.get(j+1).conta==sort.get(j).conta){ // se a palavra for igual
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
                        output=output+lexical_order[t].palavra+" ";
                    }
                    else{
                         output=output+lexical_order[t].palavra+"\n";
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
                        output=output+max[t].palavra+" ";
                    }
                    else{
                         output=output+max[t].palavra+"\n";
                    }
                       
                }
                

        }
        
    }
        System.out.print(output);
    }
          

    static void radixsort(Unsorted lista_princ, int n) 
    { 
        // Find the maximum number to know number of digits 
        int m = getMax(lista_princ, n);
  
        // Do counting sort for every digit. Note that instead 
        // of passing digit number, exp is passed. exp is 10^i 
        // where i is current digit number 
        for (int exp = 1; m/exp > 0; exp *= 10) 
            countSort(lista_princ, n, exp); 
    } 
    
    static void radixsort2(Unsorted lista_princ, int n) 
    { 
        // Find the maximum number to know number of digits 
        int m = getMax(lista_princ, n); 
  
        // Do counting sort for every digit. Note that instead 
        // of passing digit number, exp is passed. exp is 10^i 
        // where i is current digit number 
        for (int exp = 1; m/exp > 0; exp *= 10) 
            countSort2(lista_princ, n, exp); 
    } 
    
    static int getMax(Unsorted lista_princ, int n) 
    { 
        int mx = lista_princ.get(0).conta; 
        for (int i = 1; i < n; i++) 
            if (lista_princ.get(i).conta > mx) 
                mx = lista_princ.get(i).conta; 
        return mx; 
    } 
     static void countSort(Unsorted lista_princ, int n, int exp) 
    { 
        int output[] = new int[n]; // output array 
        int i; 
        int count[] = new int[10]; 
        Arrays.fill(count,0); 
  
        // Store count of occurrences in count[] 
        for (i = 0; i < n; i++) 
            count[ (lista_princ.get(i).conta/exp)%10 ]++; 
  
        // Change count[i] so that count[i] now contains 
        // actual position of this digit in output[] 
        for (i = 1; i < 10; i++) 
            count[i] += count[i - 1]; 
  
        // Build the output array 
        for (i = n - 1; i >= 0; i--) 
        { 
            output[count[ (lista_princ.get(i).conta/exp)%10 ] - 1] = lista_princ.get(i).conta; 
            count[ (lista_princ.get(i).conta/exp)%10 ]--; 
        } 
  
        // Copy the output array to arr[], so that arr[] now 
        // contains sorted numbers according to curent digit 
        for(int u=0;u<output.length;u++){
            output[output.length-1-u]=output[u];
        }
        
        for (i = 0; i < n; i++) {
            int muda=lista_princ.findpos(output[i]);// encontra posicao na lista do indice do output
            lista_princ.troca_pos(muda,i);
        } 
    } 
    
      static void countSort2(Unsorted lista_princ, int n, int exp) 
    { 
        int output[] = new int[n]; // output array 
        int output2[]=new int [n];
        int i; 
        int count[] = new int[10]; 
        Arrays.fill(count,0); 
  
        // Store count of occurrences in count[] 
        for (i = 0; i < n; i++) 
            count[ (lista_princ.get(i).indice/exp)%10 ]++; 
  
        // Change count[i] so that count[i] now contains 
        // actual position of this digit in output[] 
        for (i = 1; i < 10; i++) 
            count[i] += count[i - 1]; 
 
        for (i = n - 1; i >= 0; i--) 
        {  
            output[count[ (lista_princ.get(i).indice/exp)%10 ] - 1] = lista_princ.get(i).indice; 
            count[ (lista_princ.get(i).indice/exp)%10 ]--; 
        } 
  
        // Copy the output array to arr[], so that arr[] now 
        // contains sorted numbers according to curent digit 
        
       
        
         for(int u=0;u<output.length;u++){
             output2[output.length-u-1]=output[u];
         }
        
        
        
        for (i = 0; i < n; i++) {
            int muda=lista_princ.findpos_ind(output2[i]);// encontra posicao na lista do indice do output
            lista_princ.troca_pos(muda,i);
        } 
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
        //SE FOR impar
         while (i < esquerda.length) {
             lexical_order[k++]=esquerda[i++];
          
        }
        while (j < direita.length) {
           lexical_order[k++]=direita[j++];
        }
            
    }
    
    
   
    
    
    
    private static void printarray(ArrayList<Node> unsorted) {
        for (int i=0;i<unsorted.size();i++){
            System.out.println("palavra no array: "+unsorted.get(i).palavra+" com contador: "+unsorted.get(i).conta+" indice: "+unsorted.get(i).indice);
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
    
    public void troca_pos(int antiga,int nova){
        int aux;
        int aux2;
        int aux3[];
        String aux4;
        aux=this.get(antiga).conta;
        aux2=this.get(antiga).indice;
        aux3=this.get(antiga).num;
        aux4=this.get(antiga).palavra;
        this.get(antiga).conta=this.get(nova).conta;
        this.get(antiga).indice=this.get(nova).indice;
        this.get(antiga).num=this.get(nova).num;
        this.get(antiga).palavra=this.get(nova).palavra;
        this.get(nova).conta=aux;
        this.get(nova).indice=aux2;
        this.get(nova).num=aux3;
        this.get(nova).palavra=aux4;
        
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
    
    public int findpos(int nume){
        Unsorted currNode = this.head;
        int ind=0;
        while (currNode != null) { 
            if(currNode.novo.conta==nume){
                return ind;
            }
            currNode = currNode.next; 
            ind++;
            
        } 
        return ind;
    }
     public int findpos_ind(int nume){
        Unsorted currNode = this.head;
        int ind=0;
        while (currNode != null) { 
            if(currNode.novo.indice==nume){
                return ind;
            }
            currNode = currNode.next; 
            ind++;
            
        } 
        return ind;
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
            currNode = currNode.next; 
            
        } 
    } 
    
}
