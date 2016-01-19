/*
 * NewsHacker - ArticleListAdapter.java
 * Last Modified: 1/18/16 4:59 PM
 *
 * Copyright (c) 2016 Trevor Summerfield
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this
 * software and associated documentation files (the "Software"), to deal in the
 * Software without restriction, including without limitation the rights to use, copy,
 * modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
 * and to permit persons to whom the Software is furnished to do so, subject to the
 * following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR
 * OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */

package com.tsums.newshacker.adapters;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.tsums.newshacker.R;
import com.tsums.newshacker.databinding.ArticleListItemBinding;
import com.tsums.newshacker.models.CheeaunHNItem;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ArticleListAdapter extends RecyclerView.Adapter<ArticleListAdapter.ArticleViewHolder> {

    private LayoutInflater       inflater;
    private ArticleClickListener listener;
    private List<CheeaunHNItem>  articles;

    public ArticleListAdapter (ArticleClickListener listener, List<CheeaunHNItem> articles) {
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
