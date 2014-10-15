package com.wpy.multithreadeddownload;

import java.util.List;

import com.wpy.multithreadeddownload.adapter.DownloadManageAdapter;
import com.wpy.multithreadeddownload.constant.Constant;
import com.wpy.multithreadeddownload.dao.SqliteDao;
import com.wpy.multithreadeddownload.entity.FileState;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

/**
 * 
 * 项目名称：MultithreadedDownload 类名称：DownloadManageActivity 类描述： 下载管理界面 创建人：wpy
 * 创建时间：2014-10-11 下午4:04:05
 * 
 */
public class DownloadManageActivity extends Activity {

	private ListView list_download;
	/**
	 * 存放要显示列表的数据
	 */
	private List<FileState> data;
	private DownloadManageAdapter adapter;

	private SqliteDao dao;
	private UpdateReceiver receiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_downloadmanage);
		dao = new SqliteDao(this);

		receiver = new UpdateReceiver();
		receiver.registerAction(Constant.DOWNLOADMANAGEACTION);
	}

	@Override
	protected void onResume() {
		super.onResume();
		data = dao.getFileStates();
		initView();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		dao.updateFileState(data);
		unregisterReceiver(receiver);
	}

	/**
	 * 初始化控件
	 */
	private void initView() {
		list_download = (ListView) this.findViewById(R.id.list_download);
		adapter = new DownloadManageAdapter(this, data, dao);
		list_download.setAdapter(adapter);
	}

	/**
	 * 
	 * 项目名称：MultithreadedDownload 类名称：UpdateReceiver 类描述：
	 * 接收器类，用来接收后台service发送过来的下载进度 创建人：wpy 创建时间：2014-10-13 上午10:11:20
	 * 
	 */
	private class UpdateReceiver extends BroadcastReceiver {

		/**
		 * 注册广播接收器
		 * 
		 * @param action
		 */
		public void registerAction(String action) {
			IntentFilter intentFilter = new IntentFilter();
			intentFilter.addAction(action);
			registerReceiver(this, intentFilter);
		}

		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals(Constant.DOWNLOADMANAGEACTION)) {
				String url = intent.getStringExtra("url");
				int completeSize = intent.getIntExtra("completeSize", 0);
				for (int i = 0; i < data.size(); i++) {
					FileState fileState = data.get(i);
					if (fileState.getUrl().equals(url)) {
						fileState.setCompleteSize(completeSize);

						// Log.e("test>>", "Downloadmanage当前进度：" +
						// completeSize);

						data.set(i, fileState);
					}
				}
				adapter.setList(data);
				adapter.notifyDataSetChanged();
			}
		}

	}
}
