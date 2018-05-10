package com.mycompany.a3;
import java.util.Iterator;

public interface ICollection {
   void add(Object newobj);
   IIterator getIterator();
   void Delete(Object obj); //remove missless,saucers etc...
}
