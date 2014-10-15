package com.wpy.multithreadeddownload.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wpy.multithreadeddownload.R;
import com.wpy.multithreadeddownload.entity.DownloadFile;
import com.wpy.multithreadeddownload.service.DownloadService;
import com.wpy.multithreadeddownload.util.NetworkUtil;

public class ListAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	private Context context;
	private List<DownloadFile> files;
	private ViewHolder viewHolder = null;

	public ListAdapter(Context context, List<DownloadFile> files) {
		this.context = context;
		this.files = files;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return files.size();
	}

	@Override
	public Object getItem(int position) {
		return files.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.list_item, parent, false);
			viewHolder = new ViewHolder();

			viewHolder.icon = (ImageView) convertView
					.findViewById(R.id.iv_icon);
			viewHolder.resouceName = (TextView) convertView
					.findViewById(R.id.tv_name);
			viewHolder.startDownload = (Button) convertView
					.findViewById(R.id.btn_download);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		final DownloadFile file = files.get(position);
		viewHolder.icon.setImageResource(R.drawable.ico);
		viewHolder.resouceName.setText(file.getName());
		viewHolder.startDownload.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				NetworkUtil networkUtil = new NetworkUtil(context);
				if (networkUtil.isNetworkConnected()) {
					if (networkUtil.getNetworkType() == 1) {
						Intent intent = new Intent();
						intent.setClass(context, DownloadService.class);
						intent.putExtra("downloadUrl", file.getPath());
						intent.putExtra("name", file.getName());
						intent.putExtra("flag", "startDownload");
						Log.e("go", file.getName() + ">>>>>" + file.getPath());
						context.startService(intent);
					} else {
						Toast.makeText(context, "当前的网络状态不是wifi，请先设置",
								Toast.LENGTH_LONG).show();
					}

				}

			}
		});

		return convertView;
	}

	private class ViewHolder {
		public ImageView icon;
		public TextView resouceName;
		public Button startDownload;
	}
}
