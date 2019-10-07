package com.example.hp.fitkit;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

public class MealsFragment extends Fragment {

    View rootView;
    TextView tv,bananatv,breadtv,eggstv,parathatv,chapatitv,chapatidintv,requiredRange,calorie;
    Switch vegOnly;
    CheckBox banana,oats,whey,brownbread,parathas,eggs,chapattiaf,riceaf,cerealsaf,curdaf,blackCoffee,protein,chapattidin;
    CheckBox ricedin,cerealsdin,curddin,soyabean,chicken,fish,chickendin,fishdin;
    Button submit,askExpert,banplus,banminus,breadplus,breadminus,eggsplus,eggsminus,parathaplus,parathaminus,chapatiplus,chapatiminus,chapatidinplus,chapatidinminus;
    RelativeLayout eggslayout;
    LinearLayout bananall,eggsll,breadll,parathall,chapatill,chapatidinll;
    SharedPreferences sp;
    SharedPreferences.Editor edit;
    int weight,height,bcount=1,breadcount=1,eggcount=1,parathacount=1,chapaticount=1,chapatiDincount=1;
    int calbanana=105,caloats=972,caleggs=78,calbread=293,calparatha=126,calChapati=110,calRice=253,calChicken=410;
    int calfish=280,calCereals=411,calCurd=158,calBlackCoffee=4,calSoyabean=202;
    boolean isBanana,isBread,isEgg,isOats,isWheyProtein,isParatha,isChapatti,isRice,isChicken,isFish,isCereals,isCurd;
    boolean isCoffee,isSoyabean,isProtein,isChapatiDin,isRiceDin,isChickenDin,isFishDin,isCerealsDin,isCurdDin;
    int weighthigh,weightlow;
    CarouselView carouselView;

    int[] sampleImages = {R.drawable.meals, R.drawable.meals2, R.drawable.meals3, R.drawable.meals4};

    public MealsFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.meals,null);

        sp = getActivity().getSharedPreferences("My Pref", Context.MODE_PRIVATE);
        String weights = sp.getString("weight", "");
        weight = Integer.parseInt(weights);
        Double d = weight*2.2*14;
        Double d1= weight*2.2*16;
        weighthigh = Integer.valueOf(d1.intValue());
        weightlow = Integer.valueOf(d.intValue());

        vegOnly = rootView.findViewById(R.id.veg);
        banana = rootView.findViewById(R.id.banana);
        oats = rootView.findViewById(R.id.oats);
        whey = rootView.findViewById(R.id.whey_protein);
        brownbread = rootView.findViewById(R.id.brownBread);
        parathas = rootView.findViewById(R.id.Paratha);
        eggslayout = rootView.findViewById(R.id.eggsrl);
        eggs = rootView.findViewById(R.id.eggs);
        chapattiaf = rootView.findViewById(R.id.chapatti);
        riceaf = rootView.findViewById(R.id.RiceAft);
        cerealsaf = rootView.findViewById(R.id.dal);
        curdaf = rootView.findViewById(R.id.Curd);
        blackCoffee = rootView.findViewById(R.id.BlackCoffee);
        protein = rootView.findViewById(R.id.ProteinShake);
        chapattidin = rootView.findViewById(R.id.chapattiDin);
        ricedin = rootView.findViewById(R.id.RiceDin);
        cerealsdin = rootView.findViewById(R.id.dalDin);
        curddin = rootView.findViewById(R.id.CurdDin);
        submit = rootView.findViewById(R.id.submitRoutine);
        bananatv = rootView.findViewById(R.id.quantity_banana);
        banminus = rootView.findViewById(R.id.banminus);
        banplus = rootView.findViewById(R.id.banplus);
        breadtv = rootView.findViewById(R.id.quantity_bread);
        breadminus = rootView.findViewById(R.id.breadminus);
        breadplus = rootView.findViewById(R.id.breadplus);
        eggstv = rootView.findViewById(R.id.quantity_egg);
        eggsminus = rootView.findViewById(R.id.eggminus);
        eggsplus = rootView.findViewById(R.id.eggplus);
        parathatv = rootView.findViewById(R.id.quantity_parathas);
        parathaminus = rootView.findViewById(R.id.parathasminus);
        parathaplus = rootView.findViewById(R.id.parathasplus);
        chapatitv = rootView.findViewById(R.id.quantity_chapati);
        chapatiminus = rootView.findViewById(R.id.chapatiminus);
        chapatiplus = rootView.findViewById(R.id.chapatiplus);
        chapatidintv = rootView.findViewById(R.id.quantity_chapatiDin);
        chapatidinminus = rootView.findViewById(R.id.chapatiDinminus);
        chapatidinplus = rootView.findViewById(R.id.chapatiDinplus);
        soyabean = rootView.findViewById(R.id.soyabean);
        chicken = rootView.findViewById(R.id.ChickenAft);
        fish = rootView.findViewById(R.id.FishAft);
        chickendin = rootView.findViewById(R.id.ChickenDin);
        fishdin = rootView.findViewById(R.id.FishDin);
        bananall = rootView.findViewById(R.id.bananall);
        eggsll = rootView.findViewById(R.id.eggsll);
        breadll = rootView.findViewById(R.id.breadll);
        parathall = rootView.findViewById(R.id.parathall);
        chapatill = rootView.findViewById(R.id.chapatill);
        chapatidinll = rootView.findViewById(R.id.chapatidinll);

        carouselView = rootView.findViewById(R.id.carouselView);
        carouselView.setPageCount(sampleImages.length);

        carouselView.setImageListener(imageListener);

        vegOnly.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                   eggslayout.setVisibility(View.GONE);
                   chicken.setVisibility(View.GONE);
                   chickendin.setVisibility(View.GONE);
                   fish.setVisibility(View.GONE);
                   fishdin.setVisibility(View.GONE);
                }
                else
                {
                    eggslayout.setVisibility(View.VISIBLE);
                    chicken.setVisibility(View.VISIBLE);
                    chickendin.setVisibility(View.VISIBLE);
                    fish.setVisibility(View.VISIBLE);
                    fishdin.setVisibility(View.VISIBLE);
                }
            }
        });

        banana.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    isBanana=true;
                    bananall.setVisibility(View.VISIBLE);
                }
                else{
                    isBanana=false;
                    bananall.setVisibility(View.GONE);
                }
            }
        });
        eggs.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    isEgg = true;
                    eggsll.setVisibility(View.VISIBLE);
                }
                else{
                    isEgg=false;
                    eggsll.setVisibility(View.GONE);
                }
            }
        });
        brownbread.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    isBread=true;
                    breadll.setVisibility(View.VISIBLE);
                }
                else{
                    isBread=false;
                    breadll.setVisibility(View.GONE);
                }
            }
        });
        parathas.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    isParatha=true;
                    parathall.setVisibility(View.VISIBLE);
                }
                else{isParatha=false;
                    parathall.setVisibility(View.GONE);
                }
            }
        });
        chapattiaf.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    isChapatti=true;
                    chapatill.setVisibility(View.VISIBLE);
                }
                else{isChapatti=false;
                    chapatill.setVisibility(View.GONE);
                }
            }
        });
        chapattidin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    isChapatiDin=true;
                    chapatidinll.setVisibility(View.VISIBLE);
                }
                else{isChickenDin=false;
                    chapatidinll.setVisibility(View.GONE);
                }
            }
        });
        oats.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    isOats=true;
                else
                    isOats=false;
            }
        });
        whey.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    isWheyProtein=true;
                else
                    isWheyProtein=false;
            }
        });
        riceaf.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    isRice=true;
                else
                    isRice=false;
            }
        });
        chicken.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    isChicken=true;
                else
                    isChicken=false;
            }
        });
        fish.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    isFish=true;
                else
                    isFish=false;
            }
        });
        curdaf.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    isCurd=true;
                else
                    isCurd=false;
            }
        });
        cerealsaf.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    isCereals=true;
                else
                    isCereals=false;
            }
        });
        blackCoffee.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    isCoffee=true;
                else
                    isCoffee=false;
            }
        });
        soyabean.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    isSoyabean=true;
                else
                    isSoyabean=false;
            }
        });
        protein.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    isProtein=true;
                else
                    isProtein=false;
            }
        });
        chickendin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    isChickenDin=true;
                else
                    isChickenDin=false;
            }
        });
        fishdin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    isFishDin=true;
                else
                    isFishDin=false;
            }
        });
        cerealsdin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    isCerealsDin=true;
                else
                    isCerealsDin=false;
            }
        });
        ricedin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    isRiceDin=true;
                else
                    isRiceDin=false;
            }
        });
        curddin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    isCurdDin=true;
                else
                    isCurdDin=false;
            }
        });


        banplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bcount > 100) {
                    Toast.makeText(getContext(), "You cannot have more than 100 banana", Toast.LENGTH_SHORT).show();
                    return;
                } else
                    bcount = bcount + 1;
                bananatv.setText(""+bcount);
            }
        });
        banminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bcount <= 1) {
                    Toast.makeText(getContext(), "You cannot have less than 1 banana", Toast.LENGTH_SHORT).show();
                    return;
                } else
                    bcount = bcount - 1;
                bananatv.setText(""+bcount);
            }
        });

        breadplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (breadcount > 50) {
                    Toast.makeText(getContext(), "You cannot have more than 50 bread", Toast.LENGTH_SHORT).show();
                    return;
                } else
                    breadcount = breadcount + 1;
                breadtv.setText(""+breadcount);
            }
        });
        breadminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (breadcount <= 1) {
                    Toast.makeText(getContext(), "You cannot have less than 1 bread", Toast.LENGTH_SHORT).show();
                    return;
                } else
                    breadcount = breadcount - 1;
                breadtv.setText(""+breadcount);
            }
        });

        eggsplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (eggcount > 100) {
                    Toast.makeText(getContext(), "You cannot have more than 100 egg", Toast.LENGTH_SHORT).show();
                    return;
                } else
                    eggcount = eggcount + 1;
                eggstv.setText(""+eggcount);
            }
        });
        eggsminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (eggcount <= 1) {
                    Toast.makeText(getContext(), "You cannot have less than 1 egg", Toast.LENGTH_SHORT).show();
                    return;
                } else
                    eggcount = eggcount - 1;
                eggstv.setText(""+eggcount);
            }
        });

        parathaplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (parathacount > 80) {
                    Toast.makeText(getContext(), "You cannot have more than 80 parathas", Toast.LENGTH_SHORT).show();
                    return;
                } else
                    parathacount = parathacount + 1;
                parathatv.setText(""+parathacount);
            }
        });
       parathaminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (parathacount <= 1) {
                    Toast.makeText(getContext(), "You cannot have less than 1 paratha", Toast.LENGTH_SHORT).show();
                    return;
                } else
                    parathacount = parathacount - 1;
                parathatv.setText(""+parathacount);
            }
        });

        chapatiplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chapaticount > 50) {
                    Toast.makeText(getContext(), "You cannot have more than 50 chapati", Toast.LENGTH_SHORT).show();
                    return;
                } else
                    chapaticount = chapaticount + 1;
                chapatitv.setText(""+chapaticount);
            }
        });
        chapatiminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chapaticount <= 1) {
                    Toast.makeText(getContext(), "You cannot have less than 1 chapati", Toast.LENGTH_SHORT).show();
                    return;
                } else
                    chapaticount = chapaticount - 1;
                chapatitv.setText(""+chapaticount);
            }
        });

        chapatidinplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chapatiDincount > 50) {
                    Toast.makeText(getContext(), "You cannot have more than 50 chapati", Toast.LENGTH_SHORT).show();
                    return;
                } else
                    chapatiDincount = chapatiDincount + 1;
                chapatidintv.setText(""+chapatiDincount);
            }
        });
        chapatidinminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chapatiDincount <= 1) {
                    Toast.makeText(getContext(), "You cannot have less than 1 chapati", Toast.LENGTH_SHORT).show();
                    return;
                } else
                    chapatiDincount = chapatiDincount - 1;
                chapatidintv.setText(""+chapatiDincount);
            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewGroup viewGroup = rootView.findViewById(android.R.id.content);

                //then we will inflate the custom alert dialog xml that we created
                View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.my_dialog, viewGroup, false);
                askExpert = dialogView.findViewById(R.id.buttonOk);
                requiredRange = dialogView.findViewById(R.id.required_range);
                calorie = dialogView.findViewById(R.id.calorie);

                int results = calculateCalorie(isBanana,isOats,isWheyProtein,
                        isEgg,isBread,isParatha,isChapatti,isRice,
                        isChicken,isFish,isCereals,isCurd,isCoffee,
                        isSoyabean,isProtein,isChapatiDin,isRiceDin,
                        isChickenDin,isFishDin,isCerealsDin,isCurdDin);

                calorie.setText("Calorie Content is : "+ results);
                requiredRange.setText("Maintenance Range : "+weightlow+ "-" + weighthigh);
                //Now we need an AlertDialog.Builder object
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                //setting the view of the builder to our custom view that we already inflated
                builder.setView(dialogView);

                //finally creating the alert dialog and displaying it
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                askExpert.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(),ChatActivity.class);
                        startActivity(intent);
                    }
                });

            }
        });

        return rootView;
    }
    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(sampleImages[position]);
        }
    };

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.chatBox){
            Intent intent = new Intent(getContext(),ChatActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    public int calculateCalorie(boolean isBanana,boolean isOats,boolean isWheyProtein,boolean isEgg,boolean isBread,
                                boolean isParatha,boolean isChapatti,boolean isRice,boolean isChicken,boolean isFish,
                                boolean isCereals,boolean isCurd,boolean isCoffee,boolean isSoyabean,boolean isProtein,
                                boolean isChapatiDin,boolean isRiceDin,boolean isChickenDin,boolean isFishDin,boolean isCerealsDin,
                                boolean isCurdDin){
        int netCalorieCount=0;
        if(isBanana)
            netCalorieCount += (bcount*calbanana);
        if(isBread)
            netCalorieCount += (breadcount*calbread);
        if(isOats)
            netCalorieCount += caloats;
        if(isEgg)
            netCalorieCount += (eggcount*caleggs);
        if(isWheyProtein)
            netCalorieCount += 00;
        if(isParatha)
            netCalorieCount += (parathacount*calparatha);
        if(isChapatti)
            netCalorieCount += (chapaticount*calChapati);
        if(isRice)
            netCalorieCount += calRice;
        if(isChicken)
            netCalorieCount += calChicken;
        if(isFish)
            netCalorieCount += calfish;
        if(isCereals)
            netCalorieCount += calCereals;
        if(isCurd)
            netCalorieCount += calCurd;
        if(isCoffee)
            netCalorieCount += calBlackCoffee;
        if(isSoyabean)
            netCalorieCount += calSoyabean;
        if(isProtein)
            netCalorieCount += 00;
        if(isRiceDin)
            netCalorieCount += calRice;
        if(isChickenDin)
            netCalorieCount += calChicken;
        if(isChapatiDin)
            netCalorieCount += (chapatiDincount*calChapati);
        if(isFishDin)
            netCalorieCount += calfish;
        if(isCerealsDin)
            netCalorieCount += calCereals;
        if(isCurdDin)
            netCalorieCount += calCurd;
        return netCalorieCount;
    }
}
