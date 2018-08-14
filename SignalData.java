package simulate;

import java.util.Iterator;
import java.util.LinkedList;


/**
 * 用于记录数据的结构
 * @author whr
 *
 * @param <T>
 */
public class SignalData<T> implements Iterable<T>{
	public final int capacity;
	private int size;
	private LinkedList<T> data;
	
	public SignalData(int capacity) {
		this.capacity = capacity;
		this.size = 0;
		this.data = new LinkedList<T>();
	}
	
	public int size() {
		return this.size;
	}
	
	public void add(T i) {
		if(size<capacity) {
			data.addLast(i);
			size++;
		}else {
			data.removeFirst();
			data.addLast(i);
		}
	}
	
	public T get(int index) {
		return data.get(index);
	}
	
	public void clear() {
		data.clear();
		size = 0;
	}
	
	public SignalData<T> copy(){
		SignalData<T> cpy = new SignalData<>(this.capacity);
		for(T i:this) {
			cpy.add(i);
		}
		return cpy;
	}

	@Override
	public Iterator<T> iterator() {
		return data.iterator();
	}
}
