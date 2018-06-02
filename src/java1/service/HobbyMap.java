package java1.service;
import java.util.HashSet;
import java.util.Set;
import java1.common.ServerResponse;

/**
 * Created by �Ű��� on 2018/3/1.
 */
public class HobbyMap {

    private final Integer capacity = 200;
    private HobbyList[] table;
    private Set<String> keyset;
    private int[] inint;
    
    public HobbyMap(){
    	table = new  HobbyList[capacity];
    	keyset = new HashSet();
    	inint = new int[200];
    }

    public HobbyNode get(String key){

        if (keyset.isEmpty()){
            return null;
        }

        Integer hashcode = hash(key);
        HobbyList list = table[hashcode];

        HobbyNode temp = list.getHead();
        if (temp.getNext() == null){
            return null;
        }
        while (temp.getNext() != null){
            if (temp.getNext().getID().equals(key)){
                return temp.getNext();
            }
            temp = temp.getNext();
        }
        return temp;
    }

    public ServerResponse put(String key, HobbyNode value){

        if (keyset.contains(key)){
            return ServerResponse.createByErrorMsg("�Ѵ�����ͬID����Ȥ�������ظ���ӣ�");
        }

        Integer hashcode = hash(key);
        if(inint[hashcode] == 0){
            table[hashcode] = new HobbyList();
            inint[hashcode] = 1;
        }
        HobbyList list = table[hashcode];
        if (list.getHead().getNext() == null){
            list.getHead().setNext(value);
            list.setTail(value);
        } else {
            list.getTail().setNext(value);
            list.setTail(value);
        }
        keyset.add(key);

        return ServerResponse.createBySuccessMsg("�����Ȥ�ɹ���");
    }

    public ServerResponse removeHobby(String key){
        if (!keyset.contains(key)){
            return ServerResponse.createBySuccess();
        }

        ServerResponse rep;

        Integer hashcode = hash(key);
        HobbyList list = table[hashcode];
        rep = list.delHobby(key);
        if (!rep.isSuccess()){
            return rep;
        }
        keyset.remove(key);
        return ServerResponse.createBySuccessMsg("ɾ����Ȥ�ɹ���");
    }

    public Set<String> getKeyset(){
        return keyset;
    }

    private Integer hash(String key){
        return key.hashCode() % capacity;
    }
}
