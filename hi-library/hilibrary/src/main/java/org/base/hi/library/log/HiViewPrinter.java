package org.base.hi.library.log;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.base.hi.library.R;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: xuan
 * Created on 2023/2/28 13:43.
 * <p>
 * Describe: put log info display on the screen
 */
public class HiViewPrinter implements HiLogPrinter{
    private RecyclerView recyclerView;
    private LogAdapter adapter;
    private HiViewPrinterProvider viewProvider;
    

    public HiViewPrinter(Activity activity) {
        FrameLayout rootView = activity.findViewById(android.R.id.content);
        recyclerView = new RecyclerView(activity);
        adapter = new LogAdapter(LayoutInflater.from(recyclerView.getContext()));
        LinearLayoutManager layoutManager = new LinearLayoutManager(recyclerView.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        viewProvider = new HiViewPrinterProvider(rootView, recyclerView);
    }
    
    @NonNull
    public HiViewPrinterProvider getViewProvider() {
        return viewProvider;
    }

    @Override
    public void print(@NonNull @NotNull HiLogConfig config, int level, String tag, @NonNull @NotNull String printString) {
        adapter.addItems(new HiLogMo(System.currentTimeMillis(), level, tag, printString));
        recyclerView.smoothScrollToPosition(adapter.getItemCount()-1);
    }

    private static class LogAdapter extends RecyclerView.Adapter<LogViewHolder> {

        private LayoutInflater inflater;
        private List<HiLogMo> logs = new ArrayList<>();
        public LogAdapter(LayoutInflater inflater) {
            this.inflater = inflater;
        }
        
        void addItems(HiLogMo logItem) {
            logs.add(logItem);
            notifyItemInserted(logs.size() - 1);
        }

        @NonNull
        @Override
        public LogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = inflater.inflate(R.layout.hilog_item, parent, false);
            return new LogViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull LogViewHolder holder, int position) {
            HiLogMo logItem = logs.get(position);
            int color = getHighLightColor(logItem.level);
            holder.tagView.setTextColor(color);
            holder.messageView.setTextColor(color);
            
            holder.tagView.setText(logItem.getFlattened());
            holder.messageView.setText(logItem.log);
        }

        private int getHighLightColor(int logLevel) {
            int highLight;
            switch (logLevel) {
                case HiLogType.V:
                    highLight = 0xffbbbbbb;
                    break;
                case HiLogType.W:
                    highLight = 0xffffbb33;
                    break;
                case HiLogType.E:
                    highLight = 0xffff4444;
                    break;
                case HiLogType.A:
                    highLight = 0xff43535a;
                    break;
                case HiLogType.D:
                    highLight = 0xff33b5e5;
                    break;
                default:
                    highLight = 0xff99cc00;
                    break;
                    
            }
            return highLight;
        }

        @Override
        public int getItemCount() {
            return logs.size();
        }
    }

    private static class LogViewHolder extends RecyclerView.ViewHolder{
        TextView tagView;
        TextView messageView;
        public LogViewHolder(@NonNull View itemView) {
            super(itemView);
            tagView = itemView.findViewById(R.id.tag);
            messageView = itemView.findViewById(R.id.message);
        }
    }
}
