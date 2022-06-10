package com.example.android.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements CategoryRVAdapter.CategoryClickInterface  {

    //API KEY 1a50ae3e7cab4d09b0cd83bf50044604

    private RecyclerView newsRV, categoryRV;
    private ProgressBar loadingPB;
    private ArrayList<Articles> articlesArrayList;
    private ArrayList<CategoryRVModal> categoryRVModalArrayList;
    private CategoryRVAdapter categoryRVAdapter;
    private NewsRVAdapter newsRVAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newsRV = findViewById(R.id.idRVNews);
        categoryRV = findViewById(R.id.idRVCategories);
        loadingPB = findViewById(R.id.idPBLoading);
        articlesArrayList = new ArrayList<>();
        categoryRVModalArrayList = new ArrayList<>();
        newsRVAdapter = new NewsRVAdapter(articlesArrayList, this);
        categoryRVAdapter = new CategoryRVAdapter(categoryRVModalArrayList, this, this::onCategoryClick);
        newsRV.setLayoutManager(new LinearLayoutManager(this));
        newsRV.setAdapter(newsRVAdapter);
        categoryRV.setAdapter(categoryRVAdapter);
        getCategories();
        getNews("Technology");
        newsRVAdapter.notifyDataSetChanged();
    }

    private void getCategories(){
        //categoryRVModalArrayList.add(new CategoryRVModal("All","https://image.flaticon.com/icons/png/512/921/921490.png"));
        categoryRVModalArrayList.add(new CategoryRVModal("Technology","https://cdn-icons-png.flaticon.com/512/1087/1087815.png"));
        categoryRVModalArrayList.add(new CategoryRVModal("Science","https://cdn-icons.flaticon.com/png/512/1048/premium/1048971.png?token=exp=1654887865~hmac=b5a2ba6bc1a65df6e9fe807bde42a445"));
        categoryRVModalArrayList.add(new CategoryRVModal("Sports","https://cdn-icons-png.flaticon.com/512/857/857455.png"));
        categoryRVModalArrayList.add(new CategoryRVModal("General","https://cdn-icons-png.flaticon.com/512/921/921490.png"));
        categoryRVModalArrayList.add(new CategoryRVModal("Business","https://cdn-icons-png.flaticon.com/512/3412/3412953.png"));
        categoryRVModalArrayList.add(new CategoryRVModal("Entertainment","https://cdn-icons-png.flaticon.com/512/3163/3163508.png"));
        categoryRVModalArrayList.add(new CategoryRVModal("Health","https://cdn-icons-png.flaticon.com/512/2966/2966327.png"));
        categoryRVAdapter.notifyDataSetChanged();

    }

    private void getNews(String category){
        loadingPB.setVisibility(View.VISIBLE);
        articlesArrayList.clear();
        String categoryURL = "https://newsapi.org/v2/top-headlines?country=in&category="+category+"&apiKey=1a50ae3e7cab4d09b0cd83bf50044604";
        String url = "http://newsapi.org/v2/top-headlines?country=in&excludeDomains=stackoverflow.com&sortBy=publishedAt&language=en&apiKey=1a50ae3e7cab4d09b0cd83bf50044604";
        String BASE_URL = "http://newsapi.org/";
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Call<NewsModal> call;
        if (category.equals("All")){
            call = retrofitAPI.getAllNews(url);
        }
        else {
           call = retrofitAPI.getNewsByCategory(categoryURL);
        }

        call.enqueue(new Callback<NewsModal>() {
            @Override
            public void onResponse(Call<NewsModal> call, Response<NewsModal> response) {
                NewsModal newsModal = response.body();
                loadingPB.setVisibility(View.GONE);
                ArrayList<Articles> articles = newsModal.getArticles();
                for (int i=0;i<articles.size();i++){
                    articlesArrayList.add(new Articles(articles.get(i).getTitle(),articles.get(i).getDescription(),articles.get(i).getUrlToImage(),
                            articles.get(i).getUrl(),articles.get(i).getContent()));
                }
                newsRVAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<NewsModal> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Failed to get news", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onCategoryClick(int position) {
        String category = categoryRVModalArrayList.get(position).getCategory();
        getNews(category);

    }
}