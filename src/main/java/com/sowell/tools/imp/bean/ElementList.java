package com.sowell.tools.imp.bean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.dom4j.Element;

public class ElementList implements List<Element> {
	private List<Element> list;
	public ElementList() {
		list = new ArrayList<Element>();
	}
	
		@Override
		public boolean add(Element e) {
			list.add(e);
			return true;
		}
		
		@Override
		public int size() {
			return list.size();
		}
		
		@Override
		public boolean isEmpty() {
			return list.isEmpty();
		}
		
		@Override
		public boolean contains(Object o) {
			return list.contains(o);
		}
		
		@Override
		public Iterator<Element> iterator() {
			return list.iterator();
		}
		
		@Override
		public Object[] toArray() {
			return list.toArray();
		}
		
		@Override
		public <T> T[] toArray(T[] a) {
			return list.toArray(a);
		}
		
		@Override
		public boolean remove(Object o) {
			return list.remove(o);
		}
		
		@Override
		public boolean containsAll(Collection<?> c) {
			return list.contains(c);
		}
		
		@Override
		public boolean addAll(Collection<? extends Element> c) {
			return list.addAll(c);
		}
		
		@Override
		public boolean addAll(int index, Collection<? extends Element> c) {
			return list.addAll(index,c);
		}
		
		@Override
		public boolean removeAll(Collection<?> c) {
			return list.removeAll(c);
		}
		
		@Override
		public boolean retainAll(Collection<?> c) {
			return list.retainAll(c);
		}
		
		@Override
		public void clear() {
			list.clear();
			
		}
		
		@Override
		public Element get(int index) {
			return list.get(index);
		}
		
		@Override
		public Element set(int index, Element element) {
			return list.set(index, element);
		}
		
		@Override
		public void add(int index, Element element) {
			list.add(index,element);
			
		}
		
		@Override
		public Element remove(int index) {
			return list.remove(index);
		}
		
		@Override
		public int indexOf(Object o) {
			return list.indexOf(o);
		}
		
		@Override
		public int lastIndexOf(Object o) {
			return list.lastIndexOf(o);
		}
		
		@Override
		public ListIterator<Element> listIterator() {
			return list.listIterator();
		}
		
		@Override
		public ListIterator<Element> listIterator(int index) {
			return list.listIterator(index);
		}
		
		@Override
		public List<Element> subList(int fromIndex, int toIndex) {
			return list.subList(fromIndex, toIndex);
		}
		
		public List<Element> getList(){
			return list;
		}
		
		public Element get(){
			return list.get(0);
		}
		

}
