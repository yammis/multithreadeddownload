package com.wpy.multithreadeddownload;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.wpy.multithreadeddownload.adapter.ListAdapter;
import com.wpy.multithreadeddownload.constant.Constant;
import com.wpy.multithreadeddownload.entity.DownloadFile;

public class MainActivity extends ActionBarActivity {

	private ListView listView;
	private ListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initView();
	}

	/**
	 * 初始化控件
	 */
	private void initView() {
		listView = (ListView) this.findViewById(R.id.listview);

		// item数据
		List<DownloadFile> files = new ArrayList<DownloadFile>();
		DownloadFile file1 = new DownloadFile(getFileName(Constant.URL1),
				Constant.URL1);
		files.add(file1);
		DownloadFile file2 = new DownloadFile(getFileName(Constant.URL2),
				Constant.URL2);
		files.add(file2);

		LayoutInflater inflater = LayoutInflater.from(this);
		View footerView = inflater.inflate(R.layout.list_item_footeview, null);
		listView.addFooterView(footerView);

		adapter = new ListAdapter(this, files);
		listView.setAdapter(adapter);

		Button btn_manage = (Button) footerView.findViewById(R.id.btn_manage);
		btn_manage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 跳转到下载管理界面
				Intent intent = new Intent(MainActivity.this,
						DownloadManageActivity.class);
				startActivity(intent);
			}
		});
	}

	/**
	 * 根据地址获取文件名称
	 * 
	 * @param path
	 *            下载地址
	 * @return 下载文件的名称
	 */
	private String getFileName(String path) {
		int start = path.lastIndexOf("/") + 1;
		return path.substring(start);
	}
}
