Experiment 1-
class Student {
  public int age;
  public int uid;
  public int marks;
  Student()
  {
      age=20;
      uid=100;
      marks=80;
  }
  Student(int x,int y,int z)
  {
      x=age;
      y=uid;
      z=marks;
  }
  public void display()
  {
      System.out.println("age is:"+age);
      System.out.println("uid is:"+uid);
        System.out.println("marks is:"+marks);
        
      
  }
  
  public static void main(String[]args)
  { 
      Student sc=new Student();
      sc.display();
  }
        
Experiment 2-
class Area {
    public int length;
    public int breadth;

   
    public void shape(int length, int breadth) {
        int area1 = length * breadth;
        System.out.println("Area of rectangle:" + area1);
    }

  
    public void shape(int length) {
        int area2 = length * length;
        System.out.println("Area of square: " + area2);
    }

    public static void main(String[] args) {
        Area obj = new Area();
        obj.shape(5, 3);
        obj.shape(5);
    }
}
