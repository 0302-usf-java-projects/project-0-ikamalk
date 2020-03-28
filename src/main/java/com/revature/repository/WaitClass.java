package com.revature.repository;

import java.util.concurrent.atomic.AtomicBoolean;

public class WaitClass implements Runnable {

  private static Thread worker;
  private static final AtomicBoolean running = new AtomicBoolean(false);
  private static int interval =300;



  public void start() {
      worker = new Thread(this);
      worker.start();
  }

  public void stop() {
      running.set(false);
  }

  public void run() { 
      running.set(true);
      System.out.print("Please wait");
      while (running.get()) {
          try { 
              Thread.sleep(interval); 
          } catch (InterruptedException e){ 
              Thread.currentThread().interrupt();
              System.out.println(
                "Thread was interrupted, Failed to complete operation");
          }
          // do something here 
          if(running.get()) {
            System.out.print(".");
          }
       } 
  } 
}