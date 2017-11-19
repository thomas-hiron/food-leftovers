package com.thomas.foodleftovers.adapters;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.thomas.foodleftovers.R;
import com.thomas.foodleftovers.popo.Recipe;

/**
 * L'adapter de la liste des ingrédients
 */
public class RecipesAdapter extends ArrayAdapter<Recipe>
{
    private LayoutInflater mInflater;

    public RecipesAdapter(Context context)
    {
        super(context, R.layout.recipe);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent)
    {
        View view;

        /* Création de la vue */
        if (convertView == null) {
            view = mInflater.inflate(R.layout.recipe, parent, false);
        }
        /* On garde la vue transmise */
        else {
            view = convertView;
        }

        /* Ajout du nom de l'ingrédient */
        Recipe recipe = getItem(position);
        if (recipe != null) {
            /* Titre */
            ((TextView) view.findViewById(R.id.recipe_title)).setText(recipe.getTitle());

            /* Description */
            ((TextView) view.findViewById(R.id.recipe_duration)).setText(String.valueOf(recipe.getDuration()));

            /* Durée */
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                ((TextView) view.findViewById(R.id.recipe_description)).setText(Html.fromHtml(recipe.getDescription(), Html.FROM_HTML_MODE_COMPACT));
            }
            else {
                ((TextView) view.findViewById(R.id.recipe_description)).setText(Html.fromHtml(recipe.getDescription()));
            }

            /* Image */
            ImageView imageView = view.findViewById(R.id.recipe_picture);
            final ProgressBar progressBar = view.findViewById(R.id.recipe_picture_progress);
            Picasso
                    .with(view.getContext())
                    .load(recipe.getPictureUrl())
                    .resize(350, 250)
                    .centerCrop()
                    .into(imageView, new Callback()
                    {
                        @Override
                        public void onSuccess()
                        {
                            progressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError()
                        {
                            progressBar.setVisibility(View.GONE);
                        }
                    });
        }

        /* Retour de la vue */
        return view;
    }
}
