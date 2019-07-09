package demo.ricelee.letterbar;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.Collections;
import java.util.List;

public class MyAdapter extends BaseQuickAdapter<RecyclerBean, BaseViewHolder> {

    public MyAdapter(@Nullable List<RecyclerBean> data) {
        super(R.layout.item_recycler, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RecyclerBean item) {
        helper.setText(R.id.tv_name, item.getMemberInfo().getName());
        helper.setText(R.id.tv_py, String.valueOf(item.getFirstName()));

    }

    @Override
    public void addData(@NonNull RecyclerBean data) {
        super.addData(data);
        Collections.sort(mData);
    }
}
