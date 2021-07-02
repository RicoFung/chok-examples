package com.admin.tbtask.runnable;

public class TaskRunnable implements Runnable
{
	private TaskInvoke taskInvoke;

	public TaskRunnable(TaskInvoke taskInvoke)
	{
		super();
		this.taskInvoke = taskInvoke;
	}

	@Override
	public void run()
	{
//		System.out.println("执行任务1："+ TimeUtil.getCurrentMillTime());
		taskInvoke.execute();
	}

}
