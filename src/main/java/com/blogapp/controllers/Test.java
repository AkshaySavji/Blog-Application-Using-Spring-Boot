package com.blogapp.controllers;

public class Test {

	public static void main(String []args) {

		int a[]= {2,1,4,5,3};
		int t[]=new int[a.length-1];
		int j=0;
		for (int i=0;i<a.length-1;i++) {
			if(a[i]>a[i+1]) {
				t[j]=a[i];
			}
		}

	for(int i=0;i<t.length-1;i++) {
		System.out.println(t[i]);
	}
	
	}
}
