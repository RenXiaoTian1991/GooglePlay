package com.itheima.googleplay.holder;

import java.util.LinkedList;
import java.util.List;

import com.itheima.googleplay.R;
import com.itheima.googleplay.http.HttpHelper;
import com.itheima.googleplay.tools.UiUtils;

import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.AbsListView.LayoutParams;

public class HomePictureHolder extends BaseHolder<List<String>> {
	/* ��new HomePictureHolder()�ͻ���ø÷��� */
	private ViewPager viewPager;
	private List<String> datas;

	@Override
	public View initView() {
		viewPager = new ViewPager(UiUtils.getContext());
		viewPager.setLayoutParams(new AbsListView.LayoutParams(
				LayoutParams.MATCH_PARENT, UiUtils
						.getDimens(R.dimen.home_picture_height)));
		return viewPager;
	}

	/* �� holder.setData �Ż���� */
	@Override
	public void refreshView(List<String> datas) {
		this.datas = datas;
		viewPager.setAdapter(new HomeAdapter());
		viewPager.setCurrentItem(2000*datas.size());// ������ʼ��λ��   Integer.Max_Vlue/2
		viewPager.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					runTask.stop();   
					break;
				case MotionEvent.ACTION_CANCEL:  // �¼���ȡ��
				case MotionEvent.ACTION_UP:
					runTask.start();
					break;
				}
				
				return false; // viewPager �����¼� ����ֵҪ��false  
			}
		});
		runTask = new AuToRunTask();
		runTask.start();
	}
	boolean flag;
	private AuToRunTask runTask;
	public class AuToRunTask implements Runnable{

		@Override
		public void run() {
			if(flag){
				UiUtils.cancel(this);  // ȡ��֮ǰ
				int currentItem = viewPager.getCurrentItem();
				currentItem++;
				viewPager.setCurrentItem(currentItem);
				//  �ӳ�ִ�е�ǰ������
				UiUtils.postDelayed(this, 2000);// �ݹ����
			}
		}
		public void start(){
			if(!flag){
				UiUtils.cancel(this);  // ȡ��֮ǰ
				flag=true;
				UiUtils.postDelayed(this, 2000);// �ݹ����
			}
		}
		public  void stop(){
			if(flag){
				flag=false;
				UiUtils.cancel(this);
			}
		}
		
	}
	
	class HomeAdapter extends PagerAdapter {
		// ��ǰviewPager�����ж��ٸ���Ŀ
		LinkedList<ImageView> convertView=new LinkedList<ImageView>();
		// ArrayList
		@Override
		public int getCount() {
			return	Integer.MAX_VALUE;
		}

		/* �жϷ��صĶ���� ����view����Ĺ�ϵ */
		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			ImageView view=(ImageView) object;
			convertView.add(view);// ���Ƴ��Ķ��� ���ӵ����漯����
			container.removeView(view);
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			int index=position%datas.size();
			ImageView view;
			if(convertView.size()>0){
				view=convertView.remove(0);
			}else{
				view= new ImageView(UiUtils.getContext());
			}
			bitmapUtils.display(view, HttpHelper.URL + "image?name="
					+ datas.get(index));
			container.addView(view); // ���ص�view����
			return view; // ���صĶ���
		}

	}

}