package com.itheima.googleplay.fragment;

import java.util.List;

import com.itheima.googleplay.adapter.DefaultAdapter;
import com.itheima.googleplay.adapter.ListBaseAdapter;
import com.itheima.googleplay.domain.AppInfo;
import com.itheima.googleplay.holder.BaseHolder;
import com.itheima.googleplay.holder.ListBaseHolder;
import com.itheima.googleplay.protocol.AppProtocol;
import com.itheima.googleplay.tools.UiUtils;
import com.itheima.googleplay.view.BaseListView;
import com.itheima.googleplay.view.LoadingPage.LoadResult;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class AppFragment extends BaseFragment {

	private List<AppInfo> datas;
	/**
	 * �����سɹ���ʱ�� ��ʾ�Ľ���
	 */
	@Override
	public View createSuccessView() {
		BaseListView  listView=new BaseListView(UiUtils.getContext());
		listView.setAdapter(new ListBaseAdapter(datas,listView){

			@Override
			protected List<AppInfo> onload() {
				AppProtocol protocol=new AppProtocol();
				List<AppInfo> load = protocol.load(0);
				datas.addAll(load);
				return load;
			}
			
		});
		return listView;
	}

	/**
	 * ��������� ��ȡ������������
	 */
	protected LoadResult load() {
		AppProtocol protocol=new AppProtocol();
		datas = protocol.load(0);
		return checkData(datas); // ������� �����ֽ��  �ɹ�, ����,��
	}
}