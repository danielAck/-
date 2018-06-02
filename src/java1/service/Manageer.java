package java1.service;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java1.common.ServerResponse;

/**
 * Created by �Ű��� on 2018/3/1.
 */
public class Manageer {


    public ServerResponse studentAddHobby(StudentMap stuMap, HobbyMap hobbyMap, String studentID, String hobbyID){
        StudentNode stu = stuMap.get(studentID);
        HobbyNode hobby = hobbyMap.get(hobbyID);
//        ѧ�������Ȥ
        ServerResponse rep1 = stu.addHobby(hobby);
//        ��Ȥ���ѧ��
        ServerResponse rep2 = hobby.addStudent(stu);
        if (!rep1.isSuccess()){
            return rep1;
        }
        if (!rep2.isSuccess()){
            return rep2;
        }
        return ServerResponse.createBySuccessMsg("��ӳɹ���");
    }

    public ServerResponse addStudent(StudentMap stuMap, StudentNode stu){
        return stuMap.put(stu.getID(), stu);
    }

    public ServerResponse addHobbyCategory(HobbyCategoryMap hobbyCategoryMap, HobbyCategoryNode category){
        String categoryID = category.getID();
        String categoryName = category.getHobbyCategory();
        HobbyCategoryNode hobbyCategoryNode = new HobbyCategoryNode(categoryID, categoryName);
        return  hobbyCategoryMap.put(categoryID, hobbyCategoryNode);
    }

    public ServerResponse addHobby(HobbyCategoryMap hobbyCategoryMap, HobbyMap hobbyMap, HobbyNode hobby){

        String hobbyCategoryID = hobby.getHobbyCategoryID();
        String hobbyID = hobby.getID();
        String hobbyName = hobby.getHobby();

        HobbyCategoryNode hobbyCategory = hobbyCategoryMap.get(hobbyCategoryID);
        HobbyNode newHobby = new HobbyNode(hobbyID, hobbyCategoryID, hobbyName);
        hobbyCategory.addHobby(newHobby);
//        TODO �ж���ӳ��ֵ�����
        return hobbyMap.put(hobbyID, newHobby);
    }

    public ServerResponse getStudentList(StudentMap stuMap){
        List<StudentNode> stulist = new ArrayList<StudentNode>();
        Set<String> stuSet = stuMap.getKeyset();
        if (stuSet.isEmpty()){
            return ServerResponse.createByErrorMsg("ѧ���б�Ϊ�գ�");
        }
        for (String stuID : stuSet){
            StudentNode stu = stuMap.get(stuID);
            stulist.add(stu);
        }

        return ServerResponse.createBySuccessMsgAndData("���ҳɹ�", stulist);
    }

    public ServerResponse getStudentInfo(String stuID, StudentMap stuMap){
       StudentNode stu = stuMap.get(stuID);
       if (stu == null){
           return ServerResponse.createByErrorMsg("��ѧ�������ڣ�");
       }
       return ServerResponse.createBySuccessMsgAndData("���ҳɹ���", stu);
    }

    public ServerResponse getHobbyStudentList(String hobbyID, HobbyMap hobbyMap){
        Set<String> hobbySet = hobbyMap.getKeyset();
        if (hobbySet.isEmpty()){
            return ServerResponse.createByErrorMsg("��Ȥ�б�Ϊ�գ�");
        }
        HobbyNode hobby = hobbyMap.get(hobbyID);
        if (hobby == null){
            return ServerResponse.createByErrorMsg("����Ȥ�����ڣ�");
        }
        return ServerResponse.createBySuccessMsgAndData("���ҳɹ���", hobby.getStudentList());
    }

    public ServerResponse getHobbyList(HobbyMap hobbyMap){
        Set<String> hobbySet = hobbyMap.getKeyset();
        if (hobbySet.isEmpty()){
            return ServerResponse.createByErrorMsg("��Ȥ�б�Ϊ��");
        }
        List<HobbyNode> hobbyLists = new ArrayList<HobbyNode>();
        for (String hobbyID : hobbySet){
            HobbyNode hobby = hobbyMap.get(hobbyID);
            hobbyLists.add(hobby);
        }
        return ServerResponse.createBySuccessMsgAndData("���ҳɹ�", hobbyLists);
    }

    public ServerResponse getHobbyCategory(HobbyCategoryMap hobbyCategoryMap){
        Set<String> hobbyCategorySet = hobbyCategoryMap.getKeyset();
        List<HobbyCategoryNode> hobbyCategoryNodes = new ArrayList<HobbyCategoryNode>();

        if (hobbyCategorySet.isEmpty()){
            return ServerResponse.createByErrorMsg("��Ȥ���Ϊ��!");
        }
        for (String categoryID : hobbyCategorySet){
            HobbyCategoryNode hobbyCategoryNode = hobbyCategoryMap.get(categoryID);
            hobbyCategoryNodes.add(hobbyCategoryNode);
        }
        return ServerResponse.createBySuccessMsgAndData("���ҳɹ���", hobbyCategoryNodes);
    }

    public ServerResponse studentRemoveHobby(StudentMap stuMap, HobbyMap hobbyMap, String studentID, String hobbyID){
        StudentNode stu = stuMap.get(studentID);
        HobbyNode hobby = hobbyMap.get(hobbyID);
        ServerResponse rep1 = stu.removeHobby(hobbyID);
        ServerResponse rep2 = hobby.removeStudent(studentID);
        if (!rep1.isSuccess()){
            return rep1;
        }
        if (!rep2.isSuccess()){
            return rep2;
        }
        return ServerResponse.createBySuccessMsg("ɾ���ɹ���");
    }

    public ServerResponse removeStudent(StudentMap stuMap, HobbyMap hobbyMap, HobbyCategoryMap hobbyCategoryMap,
                                        String studentID){

        Set<String> hobbySet = hobbyMap.getKeyset();
        Set<String> hobbyCategorySet = hobbyCategoryMap.getKeyset();
        ServerResponse rep = new ServerResponse();

//        ��ѧ������Ȥ�ܱ����Ƴ�
        if(!hobbySet.isEmpty()){
        	for (String hobbyID : hobbySet){
                HobbyNode hobby = hobbyMap.get(hobbyID);
                if (hobby.isStudentExist(studentID)){
                    rep = hobby.removeStudent(studentID);
                    if (!rep.isSuccess()){
                        return rep;
                    }
                }
            }
            if (rep.getStatus() == null){
                return ServerResponse.createBySuccess();
            }
        }

//        ��ѧ����ѧ���б����Ƴ�
        rep = stuMap.removeStu(studentID);
        if (!rep.isSuccess()){
            return rep;
        }

        return ServerResponse.createBySuccessMsg("ɾ����ѧ���ɹ���");

    }

    public ServerResponse removeHobby(StudentMap stuMap, HobbyMap hobbyMap, HobbyCategoryMap hobbyCategoryMap,
                                      String hobbyID, String hobbyCategoryID){

        Set<String> studentSet = stuMap.getKeyset();
        ServerResponse rep = new ServerResponse();
        
        if(!studentSet.isEmpty()){
//          ����Ȥ���ô�ѧ����Ȥ�б���ɾ��
            for(String stuID : studentSet){
                StudentNode stu = stuMap.get(stuID);
                if (stu.isHobbyExist(hobbyID)){
                    rep =  stu.removeHobby(hobbyID);
                }
            }
            if(rep.getStatus() != null){
            	if (!rep.isSuccess()){
                    return rep;
                }
            }
        }

//        ����Ȥ���ô���Ȥ�ܱ���ɾ��
        rep = hobbyMap.removeHobby(hobbyID);
        if (!rep.isSuccess()){
            return rep;
        }

//        ����Ȥ����Ȥ������ɾ��
        HobbyCategoryNode hobbyCategoryNode = hobbyCategoryMap.get(hobbyCategoryID);
        HobbyList hobbyList = hobbyCategoryNode.getHobbyList();
        rep = hobbyList.delHobby(hobbyID);
        if (!rep.isSuccess()){
            return rep;
        }

        return ServerResponse.createBySuccessMsg("ɾ����Ȥ�ɹ���");
    }

    public ServerResponse removeHobbyCategory(StudentMap stuMap, HobbyMap hobbyMap,
                                              HobbyCategoryMap hobbyCategoryMap, String hobbyCastegoryID){

//        ����ѧ���б��������Ȥ��ѧ����Ȥ�б���ɾ��
        Set<String> hobbySet = hobbyMap.getKeyset();
        ServerResponse rep;
        Set<String> stuSet = stuMap.getKeyset();

        for (String stuID : stuSet){
            StudentNode stu = stuMap.get(stuID);
            rep = stu.removeHobbyWithHobbyCategoryID(hobbyCastegoryID);
            if(!rep.isSuccess()){
                return rep;
            }
        }

//        �������Ȥ����Ȥ�ܱ���ɾ��
        List<String> hobbyIDs = new ArrayList<String>();
        for (String hobbyID : hobbySet){
            HobbyNode hobby = hobbyMap.get(hobbyID);
            if (hobby.getHobbyCategoryID().equals(hobbyCastegoryID)){
                hobbyIDs.add(hobby.getID());
            }
        }
        for (String ID : hobbyIDs){
            rep = hobbyMap.removeHobby(ID);
            if (!rep.isSuccess()){
                return rep;
            }
        }

//        ����Ȥ�������Ȥ������ɾ��
        rep = hobbyCategoryMap.removeHobbyCategory(hobbyCastegoryID);
        if (!rep.isSuccess()){
            return rep;
        }

        return ServerResponse.createBySuccessMsg("ɾ����Ȥ���ɹ���");
    }
    
    public ServerResponse modifyStudentBasicInfo(String stuID, String name, String sex, String grade, String major, StudentMap stuMap, HobbyMap hobbyMap){

//      �ж�������Ϣ�Ƿ���ȷ
      StudentNode oldStu = stuMap.get(stuID);
      
      if(name.equals("") || sex.equals("") || grade.equals("") || major.equals("")){
          return ServerResponse.createByErrorMsg("������ѧ����Ϣ��");
      }
      if (!sex.equals("��") && !sex.equals("Ů")){
          return ServerResponse.createByErrorMsg("�Ա���������");
      }
      if (!grade.equals("��һ") && !grade.equals("���") && !grade.equals("����") && !grade.equals("����")){
          return ServerResponse.createByErrorMsg("�꼶�������������ʽ����һ/���/����/����");
      }
      
//      �޸�ѧ����Ȥ
      oldStu.modifyBasicInfo(name, sex, grade, major);
      
//      �޸���Ȥ�б��е�ѧ����Ϣ
      Set<String> set = hobbyMap.getKeyset();
      for(String key : set){
    	  HobbyNode hobby = hobbyMap.get(key);
    	  StudentList list = hobby.getStudentList();
    	  if(list != null && list.getHead().getNext() != null){
    		  StudentNode temp = list.getHead();
    		  while(temp.getNext() != null){
    			  if(temp.getNext().getID().equals(stuID)){
    				  temp.getNext().modifyBasicInfo(name, sex, grade, major);
    			  }
    			  temp = temp.getNext();
    		  }
    	  }
      }
      return ServerResponse.createBySuccess();
      
  }
    
    
//    �޸���Ȥ��Ϣ
    public ServerResponse modifyHobbyInfo(String hobbyID, String hobbyName, String hobbyCategoryID, StudentMap stuMap, HobbyMap hobbyMap, HobbyCategoryMap hobbyCategoryMap){
        HobbyNode newHobby = new HobbyNode(hobbyID, hobbyCategoryID, hobbyName);
    	HobbyNode oldHobby = hobbyMap.get(hobbyID);
        String categoryID = oldHobby.getHobbyCategoryID();
        ServerResponse rep;

        if (hobbyID.equals("") || hobbyName.equals("") || hobbyCategoryID.equals("")){
            return ServerResponse.createByErrorMsg("��������Ϣ��");
        }
//        TODO �����鷳�������޸����ݽṹ����Node���Ϊ��������next��������������һ���޸�ȫ���޸�

//        ֻ�޸�����Ȥ��
        if (oldHobby.getHobbyCategoryID().equals(hobbyCategoryID)){
            Set<String> set = stuMap.getKeyset();
            for (String ID : set){      //  �޸�ѧ����Ӧ��Ȥ��Ϣ
                StudentNode stu = stuMap.get(ID);
                HobbyList list = stu.getHobbyList();
                if (list != null && list.getHead().getNext() != null){
                    HobbyNode temp = list.getHead();
                    while (temp.getNext() != null){
                        if (temp.getNext().getID().equals(hobbyID)){
                            temp.getNext().setHobby(hobbyName);
                        }
                        temp = temp.getNext();
                    }
                }
            }
            rep = oldHobby.modifyHobbyInfo(newHobby);     //�޸���Ȥ��Ϣ
            if (!rep.isSuccess()){
                return rep;
            }
            rep = hobbyCategoryMap.get(categoryID).modifyHobby(hobbyID, newHobby);   //�޸���Ȥ�б�����Ȥ��Ϣ
            if (!rep.isSuccess()){
                return rep;
            }
            return  ServerResponse.createBySuccessMsg("�޸ĳɹ���");

        } else {    // �޸�����Ȥ�������
            String oldHobbyCategoryID = oldHobby.getHobbyCategoryID();
            String newHobbyCategoryID = newHobby.getHobbyCategoryID();
            newHobby.setStudentList(oldHobby.getStudentList());

//            �޸�ѧ���б�����Ӧ����Ȥ
            Set<String> set = stuMap.getKeyset();
            for (String ID : set){
                StudentNode stu = stuMap.get(ID);
                HobbyList list = stu.getHobbyList();
                if (list != null && list.getHead().getNext() != null){
                    HobbyNode temp = list.getHead();
                    while (temp.getNext() != null){
                        if (temp.getNext().getID().equals(newHobby.getID())){
                            temp.getNext().setHobby(newHobby.getHobby());
                            temp.getNext().setHobbyCategoryID(newHobbyCategoryID);
                        }
                        temp = temp.getNext();
                    }
                }
            }
//            ��ԭ������Ȥ������ɾ������Ȥ
            HobbyCategoryNode hobbyCategoryNode = hobbyCategoryMap.get(oldHobbyCategoryID);
            HobbyList hobbyList = hobbyCategoryNode.getHobbyList();
            rep = hobbyList.delHobby(hobbyID);
            if (!rep.isSuccess()){
                return rep;
            }
//            ���޸ĺ����Ȥ��ӵ���Ӧ��Ȥ�����
            hobbyCategoryNode = hobbyCategoryMap.get(newHobbyCategoryID);
            rep = hobbyCategoryNode.addHobby(newHobby);
            if (!rep.isSuccess()){
                return rep;
            }
        }

        return ServerResponse.createBySuccessMsg("�޸ĳɹ���");
    }
    
    public ServerResponse modifyHobbyCategory(String categoryID, HobbyCategoryNode newHobbyCategory, HobbyCategoryMap hobbyCategoryMap){
        HobbyCategoryNode oldCategory = hobbyCategoryMap.get(categoryID);
        return oldCategory.modifyHobbyCategory(newHobbyCategory);
    }
    
    
}
