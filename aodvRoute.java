class RREQ{
	static int sAddr;
	static int dAddr;
	static int seqNo;
	static int hopCount;

/*	public RREQ	{
		sAddr=0;
		dAddr=0;
		seqNo=0;
		hopCount=0;
	} */
	public void routeRequest(Node node[], RREQ rreq){
		int flag=0,flag2=0, nodeNum=0;	
		RREP rrep = new RREP();

		//finding the source node
		for(int i=0;i<5;i++){
			if(node[i].name == rreq.sAddr){
				flag=1;
				nodeNum=i;
				break;
			}			
		}
		
		//if node found 
		if(flag==1){
			for(int i=nodeNum;i<5;i++){
				for(int j=0;j<5;j++){
					int current=node[i].adjacent[j];
					//destination check
					if(node[current].name == rreq.dAddr){
						flag2=1;
						//update routing table
						node[current].updateRT(rreq, node, i);
						rreq.hopCount++;
						//route reply 		
						for(int k=rreq.hopCount;k<0;k--){
							rrep.routeReply(rreq);
							
						}
						break;
					}
					else{
						//update routing table
						node[current].updateRT(rreq, node, i);
						rreq.hopCount++;	
					}
				}				
			}
		}
	}
}

class Node {
	int name;

	//values of routing table	
	//int sAddr[10];
		
	int next[]=new int[10];
	int dAddr[]=new int[10];
	int seqNo[]=new int[10];
	int hopCount[]=new int[10];


	int adjacent[]=new int[10];
	int adjacentCount;
	int routingCount=0;
	
	public void updateRT(RREQ rreq, Node node[],int i){
		dAddr[routingCount]=rreq.sAddr;
		next[routingCount]=node[i].name;
		seqNo[routingCount]=rreq.seqNo;
		hopCount[routingCount]=rreq.hopCount;
		routingCount++;
	}

	public void updateRTrrep(RREP rrep,Node node[], int j){
		dAddr[routingCount]=rrep.sAddr;
		next[routingCount]=node[j].name;
		seqNo[routingCount]=rrep.seqNo;
		hopCount[routingCount]=rrep.hopCount;
		routingCount++;
	}
}

class RREP{
	static int sAddr;
	static int dAddr;
	static int seqNo;
	static int hopCount=-1;

	public void routeReply(RREQ rreq){
		sAddr=rreq.dAddr;
		dAddr=rreq.sAddr;
//		seqNo=
		hopCount++;
	}	
		
}

class MessagePacket{
	int sourceId;
	int destId;
	String data;
}

public class aodvRoute	{
	public static void main(String args[]){

		RREQ rreq = new RREQ();	
		rreq.sAddr=0;
		rreq.dAddr=0;
		rreq.seqNo=0;
		rreq.hopCount=0;

		//A 5 node map
		Node node[] = new Node[5];

		

		//Sending Route Request messsage
		rreq.routeRequest(node, rreq);
		
		
	}
}
