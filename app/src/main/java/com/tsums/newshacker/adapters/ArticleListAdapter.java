package com.tsums.newshacker.adapters;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.tsums.newshacker.R;
import com.tsums.newshacker.databinding.ArticleListItemBinding;
import com.tsums.newshacker.models.HNItem;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ArticleListAdapter extends RecyclerView.Adapter<ArticleListAdapter.ArticleViewHolder> {

    private LayoutInflater       inflater;
    private ArticleClickListener listener;
    private List<HNItem>         articles;

    public ArticleListAdapter (ArticleClickListener listener, List<HNItem> articles) {
        this.articles = articles;
        this.listener = listener;
    }

    @Override
    public ArticleViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {

        if (inflater == null) {
            inflater = LayoutInflater.from(parent.getContext());
        }

        ArticleListItemBinding newBinding = DataBindingUtil.inflate(inflater, R.layout.article_list_item, parent, false);
        return new ArticleViewHolder(newBinding);
    }

    @Override
    public void onBindViewHolder (ArticleViewHolder holder, int position) {
        holder.binding.setItem(articles.get(position));
    }

    @Override
    public long getItemId (int position) {
        return articles.get(position).id;
    }

    @Override
    public int getItemCount () {
        return articles.size();
    }

    public interface ArticleClickListener {

        void onClick (int position);
    }

    class ArticleViewHolder extends RecyclerView.ViewHolder {

        private ArticleListItemBinding binding;

        public ArticleViewHolder (ArticleListItemBinding binding) {
            super(binding.getRoot());
            ButterKnife.bind(this, binding.getRoot());
            this.binding = binding;
        }

        @OnClick (R.id.article_list_item_container)
        void onClickItem () {
            listener.onClick(getAdapterPosition());
        }

    }

}
