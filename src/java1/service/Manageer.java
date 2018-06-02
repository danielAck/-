package java1.service;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java1.common.ServerResponse;

/**
 * Created by 张柏桦 on 2018/3/1.
 */
public class Manageer {


    public ServerResponse studentAddHobby(StudentMap stuMap, HobbyMap hobbyMap, String studentID, String hobbyID){
        StudentNode stu = stuMap.get(studentID);
        HobbyNode hobby = hobbyMap.get(hobbyID);
//        学生添加兴趣
        ServerResponse rep1 = stu.addHobby(hobby);
//        兴趣添加学生
        ServerResponse rep2 = hobby.addStudent(stu);
        if (!rep1.isSuccess()){
            return rep1;
        }
        if (!rep2.isSuccess()){
            return rep2;
        }
        return ServerResponse.createBySuccessMsg("添加成功！");
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
//        TODO 判断添加出现的问题
        return hobbyMap.put(hobbyID, newHobby);
    }

    public ServerResponse getStudentList(StudentMap stuMap){
        List<StudentNode> stulist = new ArrayList<StudentNode>();
        Set<String> stuSet = stuMap.getKeyset();
        if (stuSet.isEmpty()){
            return ServerResponse.createByErrorMsg("学生列表为空！");
        }
        for (String stuID : stuSet){
            StudentNode stu = stuMap.get(stuID);
            stulist.add(stu);
        }

        return ServerResponse.createBySuccessMsgAndData("查找成功", stulist);
    }

    public ServerResponse getStudentInfo(String stuID, StudentMap stuMap){
       StudentNode stu = stuMap.get(stuID);
       if (stu == null){
           return ServerResponse.createByErrorMsg("该学生不存在！");
       }
       return ServerResponse.createBySuccessMsgAndData("查找成功！", stu);
    }

    public ServerResponse getHobbyStudentList(String hobbyID, HobbyMap hobbyMap){
        Set<String> hobbySet = hobbyMap.getKeyset();
        if (hobbySet.isEmpty()){
            return ServerResponse.createByErrorMsg("兴趣列表为空！");
        }
        HobbyNode hobby = hobbyMap.get(hobbyID);
        if (hobby == null){
            return ServerResponse.createByErrorMsg("该兴趣不存在！");
        }
        return ServerResponse.createBySuccessMsgAndData("查找成功！", hobby.getStudentList());
    }

    public ServerResponse getHobbyList(HobbyMap hobbyMap){
        Set<String> hobbySet = hobbyMap.getKeyset();
        if (hobbySet.isEmpty()){
            return ServerResponse.createByErrorMsg("兴趣列表为空");
        }
        List<HobbyNode> hobbyLists = new ArrayList<HobbyNode>();
        for (String hobbyID : hobbySet){
            HobbyNode hobby = hobbyMap.get(hobbyID);
            hobbyLists.add(hobby);
        }
        return ServerResponse.createBySuccessMsgAndData("查找成功", hobbyLists);
    }

    public ServerResponse getHobbyCategory(HobbyCategoryMap hobbyCategoryMap){
        Set<String> hobbyCategorySet = hobbyCategoryMap.getKeyset();
        List<HobbyCategoryNode> hobbyCategoryNodes = new ArrayList<HobbyCategoryNode>();

        if (hobbyCategorySet.isEmpty()){
            return ServerResponse.createByErrorMsg("兴趣类别为空!");
        }
        for (String categoryID : hobbyCategorySet){
            HobbyCategoryNode hobbyCategoryNode = hobbyCategoryMap.get(categoryID);
            hobbyCategoryNodes.add(hobbyCategoryNode);
        }
        return ServerResponse.createBySuccessMsgAndData("查找成功！", hobbyCategoryNodes);
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
        return ServerResponse.createBySuccessMsg("删除成功！");
    }

    public ServerResponse removeStudent(StudentMap stuMap, HobbyMap hobbyMap, HobbyCategoryMap hobbyCategoryMap,
                                        String studentID){

        Set<String> hobbySet = hobbyMap.getKeyset();
        Set<String> hobbyCategorySet = hobbyCategoryMap.getKeyset();
        ServerResponse rep = new ServerResponse();

//        将学生从兴趣总表中移除
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

//        将学生从学生列表中移除
        rep = stuMap.removeStu(studentID);
        if (!rep.isSuccess()){
            return rep;
        }

        return ServerResponse.createBySuccessMsg("删除该学生成功！");

    }

    public ServerResponse removeHobby(StudentMap stuMap, HobbyMap hobbyMap, HobbyCategoryMap hobbyCategoryMap,
                                      String hobbyID, String hobbyCategoryID){

        Set<String> studentSet = stuMap.getKeyset();
        ServerResponse rep = new ServerResponse();
        
        if(!studentSet.isEmpty()){
//          将兴趣爱好从学生兴趣列表中删除
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

//        将兴趣爱好从兴趣总表中删除
        rep = hobbyMap.removeHobby(hobbyID);
        if (!rep.isSuccess()){
            return rep;
        }

//        将兴趣从兴趣类别表中删除
        HobbyCategoryNode hobbyCategoryNode = hobbyCategoryMap.get(hobbyCategoryID);
        HobbyList hobbyList = hobbyCategoryNode.getHobbyList();
        rep = hobbyList.delHobby(hobbyID);
        if (!rep.isSuccess()){
            return rep;
        }

        return ServerResponse.createBySuccessMsg("删除兴趣成功！");
    }

    public ServerResponse removeHobbyCategory(StudentMap stuMap, HobbyMap hobbyMap,
                                              HobbyCategoryMap hobbyCategoryMap, String hobbyCastegoryID){

//        迭代学生列表，将相关兴趣从学生兴趣列表中删除
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

//        将相关兴趣从兴趣总表中删除
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

//        将兴趣大类从兴趣类别表中删除
        rep = hobbyCategoryMap.removeHobbyCategory(hobbyCastegoryID);
        if (!rep.isSuccess()){
            return rep;
        }

        return ServerResponse.createBySuccessMsg("删除兴趣类别成功！");
    }
    
    public ServerResponse modifyStudentBasicInfo(String stuID, String name, String sex, String grade, String major, StudentMap stuMap, HobbyMap hobbyMap){

//      判断输入信息是否正确
      StudentNode oldStu = stuMap.get(stuID);
      
      if(name.equals("") || sex.equals("") || grade.equals("") || major.equals("")){
          return ServerResponse.createByErrorMsg("请完整学生信息！");
      }
      if (!sex.equals("男") && !sex.equals("女")){
          return ServerResponse.createByErrorMsg("性别输入有误！");
      }
      if (!grade.equals("大一") && !grade.equals("大二") && !grade.equals("大三") && !grade.equals("大四")){
          return ServerResponse.createByErrorMsg("年级输入有误，输入格式：大一/大二/大三/大四");
      }
      
//      修改学生兴趣
      oldStu.modifyBasicInfo(name, sex, grade, major);
      
//      修改兴趣列表中的学生信息
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
    
    
//    修改兴趣信息
    public ServerResponse modifyHobbyInfo(String hobbyID, String hobbyName, String hobbyCategoryID, StudentMap stuMap, HobbyMap hobbyMap, HobbyCategoryMap hobbyCategoryMap){
        HobbyNode newHobby = new HobbyNode(hobbyID, hobbyCategoryID, hobbyName);
    	HobbyNode oldHobby = hobbyMap.get(hobbyID);
        String categoryID = oldHobby.getHobbyCategoryID();
        ServerResponse rep;

        if (hobbyID.equals("") || hobbyName.equals("") || hobbyCategoryID.equals("")){
            return ServerResponse.createByErrorMsg("请完善信息！");
        }
//        TODO 遍历麻烦，考虑修改数据结构，将Node拆分为数据区和next区，数据区共享，一次修改全部修改

//        只修改了兴趣名
        if (oldHobby.getHobbyCategoryID().equals(hobbyCategoryID)){
            Set<String> set = stuMap.getKeyset();
            for (String ID : set){      //  修改学生相应兴趣信息
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
            rep = oldHobby.modifyHobbyInfo(newHobby);     //修改兴趣信息
            if (!rep.isSuccess()){
                return rep;
            }
            rep = hobbyCategoryMap.get(categoryID).modifyHobby(hobbyID, newHobby);   //修改兴趣列表中兴趣信息
            if (!rep.isSuccess()){
                return rep;
            }
            return  ServerResponse.createBySuccessMsg("修改成功！");

        } else {    // 修改了兴趣所属类别
            String oldHobbyCategoryID = oldHobby.getHobbyCategoryID();
            String newHobbyCategoryID = newHobby.getHobbyCategoryID();
            newHobby.setStudentList(oldHobby.getStudentList());

//            修改学生列表中相应的兴趣
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
//            从原来的兴趣类别表中删除该兴趣
            HobbyCategoryNode hobbyCategoryNode = hobbyCategoryMap.get(oldHobbyCategoryID);
            HobbyList hobbyList = hobbyCategoryNode.getHobbyList();
            rep = hobbyList.delHobby(hobbyID);
            if (!rep.isSuccess()){
                return rep;
            }
//            将修改后的兴趣添加到对应兴趣类别中
            hobbyCategoryNode = hobbyCategoryMap.get(newHobbyCategoryID);
            rep = hobbyCategoryNode.addHobby(newHobby);
            if (!rep.isSuccess()){
                return rep;
            }
        }

        return ServerResponse.createBySuccessMsg("修改成功！");
    }
    
    public ServerResponse modifyHobbyCategory(String categoryID, HobbyCategoryNode newHobbyCategory, HobbyCategoryMap hobbyCategoryMap){
        HobbyCategoryNode oldCategory = hobbyCategoryMap.get(categoryID);
        return oldCategory.modifyHobbyCategory(newHobbyCategory);
    }
    
    
}
