package classes;
import sun.security.util.Length;

import java.sql.*;
import java.time.LocalDate;
import java.util.*;
import java.util.Date;

public class DBAccess {
    static final String db_URL="jdbc:sqlserver://DESKTOP-E4CK7B2;DatabaseName=javaSql";
    static final String User="DESKTOP-E4CK7B2/hadas";
    static final String PASS=null;
    static Connection connection=null;
    Statement statement=null;

    public List<friends> getF() {
        return f;
    }

    public void setF(List<friends> f) {
        this.f = f;
    }

    public List<task> getT() {
        return t;
    }

    public void setT(List<task> t) {
        this.t = t;
    }

    public static List<friends> f=new ArrayList<friends>();
    public static List<task> t=new ArrayList<task>();

    public DBAccess() {
        fillF();
        fillT();
    }

    public static void fillF() {
        f.add(new friends(1,"shalom","shalomlach@gmail.com"));
        f.add(new friends(2,"israel","israellevi@gmail.com"));
        f.add(new friends(3,"yosef","yosefcohen@gmail.com"));
    }
    public static void fillT()
    {
        t.add(new task(1,"clean","clean all rooms",statuses.NEW,1,1,new Date(),new Date(23/8/2020)));
        t.add(new task(2,"cook","cooking a cake",statuses.INPROGRES,2,1,new Date(20/8/2020),new Date(23/8/2020)));
        t.add(new task(3,"buy","buying bread",statuses.DONE,3,1,new Date(),new Date(23/8/2020)));
    }

    public boolean isEmpty() {
            return false;
        }

        public boolean contains(Object o) {
            return false;
        }

        public Iterator<friends> iterator() {
            return null;
        }

        public Object[] toArray() {
            return new Object[0];
        }

        public <T> T[] toArray(T[] a) {
            return null;
        }

        public boolean add(friends friends) {
            return false;
        }

        public boolean remove(Object o) {
            return false;
        }

        public boolean containsAll(Collection<?> c) {
            return false;
        }

        public boolean addAll(Collection<? extends friends> c) {
            return false;
        }

        public boolean addAll(int index, Collection<? extends friends> c) {
            return false;
        }

        public boolean removeAll(Collection<?> c) {
            return false;
        }

        public boolean retainAll(Collection<?> c) {
            return false;
        }

        public void clear() {

        }

        public friends get(int index) {
            return null;
        }

        public friends set(int index, friends element) {
            return null;
        }

        public void add(int index, friends element) {

        }

        public friends remove(int index) {
            return null;
        }

        public int indexOf(Object o) {
            return 0;
        }

        public int lastIndexOf(Object o) {
            return 0;
        }

        public ListIterator<friends> listIterator() {
            return null;
        }

        public ListIterator<friends> listIterator(int index) {
            return null;
        }

        public List<friends> subList(int fromIndex, int toIndex) {
            return null;
        }


    //select all tasks
    public List<task> geAlltTasks(){
        String sql= "select * from task";
        try{
            connection= DriverManager.getConnection(db_URL,User,PASS);
            PreparedStatement ps=connection.prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            String strSt = rs.getString("taskStatus");
            statuses s = statuses.valueOf(strSt);
            List<task> taskList= new ArrayList<task>();
            while (rs.next())
            {
                taskList.add(new task(rs.getInt("code"), rs.getString("taskTitle"), rs.getString("taskDescription"),
                        s, rs.getInt("belongTo"), rs.getDouble("estimatedHour") ,rs.getDate("startDate"),
                        rs.getDate("endDate")));
            }

            return taskList;
        }
        catch (SQLException throwables){
            return this.getT();
        }
    }

    //select all tasks for one friend
    public List<task> getTaskByDM(int dmCode){
        String sql= "select * from task where belongTo="+ dmCode;
        List<task> taskList= new ArrayList<task>();
        try{
            connection= DriverManager.getConnection(db_URL,User,PASS);
            PreparedStatement ps=connection.prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            String strSt = rs.getString("taskStatus");
            statuses s = statuses.valueOf(strSt);
            while (rs.next())
            {
                taskList.add(new task(rs.getInt("code"), rs.getString("taskTitle"), rs.getString("taskDescription"),
                        s, rs.getInt("belongTo"), rs.getDouble("estimatedHour") ,rs.getDate("startDate"),
                        rs.getDate("endDate")));
            }

            return taskList;
        }
        catch (SQLException throwables){
            for(int i=0;i<t.size();i++)
            {
                if(t.get(i).getBelongTo()==dmCode)
                taskList.add(t.get(i));
            }
            return  taskList;
        }
    }
    //insert new task
    public void inertTask(task t){

        String sql="INSERT INTO task values(?,?,?,?,?,?,?) ";
        try{
            connection= DriverManager.getConnection(db_URL,User,PASS);
            PreparedStatement ps=connection.prepareStatement(sql);
            ps.setInt(1, t.getCode());
            ps.setString(2, t.getTaskTitle());
            ps.setString(3, t.getTaskDescription());
            ps.setInt(4, t.getBelongTo());
            ps.setDouble(5, t.getEstimatedHour());
            ps.setDate(6, (java.sql.Date) t.getStartDate());
            ps.setDate(7, (java.sql.Date) t.getEndDate());
            ResultSet rs = ps.executeQuery();

        }
        catch (SQLException throwables){
            this.t.add(t);
        }
    }

    //update status for task
    public void updateStatus(statuses s,int taskCode){
        String sql="update task set taskStatus="+s.toString()+" where code="+taskCode;
        try{
            connection= DriverManager.getConnection(db_URL,User,PASS);
            PreparedStatement ps=connection.prepareStatement(sql);
            int rs=ps.executeUpdate();
        }
        catch (SQLException throwables){
            for(int i=0;i<this.t.size();i++)
                if(this.t.get(i).getCode()==taskCode)
                    this.t.get(i).setStatus(s);
        }
    }

    //select friends
    public List<friends> getAllFriends(){
        String sql= "select * from friends";
        try{
            connection= DriverManager.getConnection(db_URL,User,PASS);
            PreparedStatement ps=connection.prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            List<friends> friendsList= new ArrayList<friends>();
            while (rs.next())
            {
                friendsList.add(new friends(rs.getInt("code"), rs.getString("fullName"), rs.getString("email")));
            }
            return friendsList;
        }
        catch (SQLException throwables){
            fillF();
            return this.getF();
        }
    }

    public static void initializing() throws ClassNotFoundException, SQLException {
        fillF();
        fillT();
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        System.out.println("Connecting to a selected database...");
        try {
            connection = DriverManager.getConnection(db_URL, User, PASS);
            Statement sta = connection.createStatement();
            String Sql1 = "select * from Tasks";
            String Sql2 = "select * from TeamMember";

            ResultSet rs1 = sta.executeQuery(Sql1);
            ResultSet rs2 = sta.executeQuery(Sql2);
            String strSt = rs1.getString("taskStatus");
            statuses s = statuses.valueOf(strSt);
            while (rs1.next()) {
                int mCode = rs1.getInt("MemberCode");
                friends x1 = new friends();
                x1.setCode(
                        (Integer) mCode
                );
                t.add(new task(rs1.getInt("code"), rs1.getString("taskTitle"), rs1.getString("taskDescription"),
                        s, rs1.getInt("belongTo"), rs1.getDouble("estimatedHour"), rs1.getDate("startDate"),
                        rs1.getDate("endDate")));
            }
            while (rs2.next()) {
                f.add(new friends(rs2.getInt("code"), rs2.getString("fullName"), rs2.getString("email")));
            }

        } catch (Exception e) {
            System.out.println("cannot connect to sql");

        }
    }
}