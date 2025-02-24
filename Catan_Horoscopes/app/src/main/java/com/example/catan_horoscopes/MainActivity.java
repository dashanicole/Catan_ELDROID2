package com.example.catan_horoscopes;

import cn.pedant.SweetAlert.SweetAlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.catan_horoscopes.ItemAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // Horoscope data
    String[] zodiacSigns = {
            "AQUARIUS", "PISCES", "ARIES", "TAURUS", "GEMINI", "CANCER", "LEO",
            "VIRGO", "LIBRA", "SCORPIO", "SAGITTARIUS", "CAPRICORN"
    };

    String[] dateRanges = {
            "January 20 - February 18", "February 19 - March 20",
            "March 21 - April 19", "April 20 - May 20", "May 21 - June 20",
            "June 21 - July 22", "July 23 - August 22", "August 23 - September 22",
            "September 23 - October 22", "October 23 - November 21",
            "November 22 - December 21", "December 22 - January 19"
    };

    String[] horoscopes = {
            "The fixed air sign is making plenty of headlines as we head into what is been coined the \"Age of Aquarius.\" Quirky, generally progressive, skeptical, and social (albeit in a cool, aloof, friends-with-everyone way), those with the Water Bearers influence in their charts are wired to prioritize \"we\" over \"me,\" gravitating to causes and activities that hold the greater good of society as a whole in mind. (Look no further than Alicia Keys, who has a stellium, meaning three or more signs, in Aquarius: her sun, Mercury, and Mars.) They tend to gravitate to more platonic relationships than deeply intimate entanglements and might even opt for non-traditional arrangements, as they love to strike out against convention whenever possible. And given electric Uranus involvement, they tend to be tech-savvy and science-minded.",
            "Imagine not only being super-tuned into your own feelings but also being wired to pick up on and take on everyone elses emotions. Now you are in the headspace of a person who has the significant presence of mutable water sign Pisces in their chart. Incredibly sensitive and intuitive, they are the healers, the hopeless romantics, the artists, and the escapists of the zodiac. While they have an instinct to get swept up in otherworldly daydreams to get away from any emotional pain, the healthiest way for them to channel these deeply felt emotions is through creative outlets like theater, music, or poetry. (The late opera-loving RBGs sun and Venus were in the water sign.) And thanks to their Neptune influence, they have keen imaginations and tend to be up for exploring all things related to spirituality, the metaphysical, and psychology.",
            "The cardinal fire sign is known for being dynamic, athletic, and having an insatiable appetite for winning. For this reason, they take great pride in being early adopters of anything and everything from the hottest new sneaker drop to the latest iPhone. And they pretty much live to compete and argue. Ram people tend to have the makings of a pro athlete, trend-setting influencer, or lawyer (former prosecutor and now Vice President Kamala Harris was born with her moon in Aries).",
            "The fixed earth sign has quite a reputation for being the most stubborn one of the zodiac, but remember, there are fixed signs in each element! Thanks to their Venusian influence, Taureans are actually fairly chill. They are known for loving luxury and indulgence, being super-loyal, and enjoying art (whether they create or just appreciate it). (Sound a bit like Ariana Grande? Although her sun is in Cancer, her Venus is in sweet Taurus.) They tend to adore spa days and sweets. They are known for taking their time — whether that means having a really long fuse to get fired up, dragging their feet to take action, or being lazy and languorous when it comes to intimacy.",
            "The word \"mercurial\" might have very well been created for Gemini, the mutable air sign that lives for communication in all forms. They are lovers of sharing whatever is on their mind, whenever, however. (Amy Schumers sun and Venus are in the loquacious air sign.) Although, do not assume they are always outgoing. They can be reserved and shy one minute and incredibly chatty the next. Given their innate mastery of language and social skills, they tend to have a wide, diverse circle of friends and gravitate to career paths that allow them to express themselves and utilize their super-buzzy brains (think marketing/PR, politics, publishing).",
            "The cardinal water sign, influenced by the shimmering maternal moon, is one of the greatest dreamers and do-ers of the zodiac. As the ruler of the Fourth House, which deals with family and home life, they are homebodies who prioritize their connections with loved ones and achieving a lasting sense of security. But their crabbiness absolutely may come into play when they are frustrated or are otherwise catapulted into a moody headspace. They will go into their self-protective \"shells,\" requiring time away from others to take care of themselves before they can get back to taking care of everyone else. (Mindy Kaling has a stellium — her sun, moon, and Mercury — in the endearing water sign.)",
            "The fixed fire sign is ruled by the confident sun, which informs their positive, cheerful vibe. Driven and self-assured leaders, they tend to be oriented toward taking action in life, and they are born feeling like they can accomplish their wildest dreams thanks to a glimmery combo of magnetism, luck, and endlessly believing in themselves. (Former President Barack Obamas sun and Mercury are in the charismatic fire sign.) Although they might find it a bit difficult to step out of being self-focused, they can be extremely loyal and adore showering their loved ones in playful energy and all of lifes finest things.",
            "The mutable earth sign might very well be mistaken as an air sign, given the influence of Mercury, which means their minds are pretty much going nonstop. Lovers of lists, spreadsheets, and blank journals, Virgos are the go-to researchers, stand-out organizers, and pretty much A students of the zodiac. They tend to be perfectionists who adore working hard to make the end result of any pursuit \"just right\" — whether that is a recipe, a professional project, or search for a partner. (Or in the case of Beyonce, whose sun is in the earth sign, the entertainment and art we cannot get enough of). Speaking of partners and loved ones, they will often bend themselves into knots to help and be of service to their nearest and dearest.",
            "The cardinal air sign was born to bring balance, harmony, and justice to their work and relationships. Given their Venusian influence, they are lovers of art and beauty who are known for being social butterflies and the ultimate hosts. And as the ruler of the Seventh House of Partnership, they prioritize one-on-one bonds, especially of the romantic variety. Although they tend to be interested in achieving serenity at all costs and connecting with a wide range of people, they are go-getters (Serena Williams is one!) who will stand up for what they believe in, putting in the time and energy to ensure a fair result.",
            "The fixed water sign is known for being one of — if not the — most private sign in the zodiac. Co-ruled by transformative Pluto and go-getter Mars, they are able to command peoples attention with their intense, powerful presence and air of mystery. They are also very much in touch with their spirituality and sexuality, but they hold their cards close to their chest. (Fiercely private family man Ryan Gosling has his sun and Mercury in the water sign.) Even after being in a relationship (platonic, romantic, or business-related) with a Scorpio for years, you might not know the whole story behind their emotional wounds and, at times, rough-around-the-edges tone. But once they are in any kind of emotional entanglement, the resolute, razor-focused sign is in it.",
            "Ruled by fortunate Jupiter, which brings a magnifying effect to everything it touches, Sagittarians are big, life-loving personalities who adore globe-trotting, being at the heart of any party, and exploring as much as life has to offer. They are also born philosophers who are endlessly passionate about their beliefs and have a tendency to hop on a soapbox frequently in order to share their worldview, often in a way that pulls no punches. (President Biden has his ascendant in Sag, which is why he was known for his no-nonsense rhetoric. \"Folks!\") They are natural-born comedians, entertainers, politicians, and/or generally gravitate to career paths that allow for lots of travel.",
            "If you want someone who is perpetually motivated to achieve on your team, you are going to want to tap someone whose chart includes the cardinal earth sign Capricorn. People born with Cap are on a lifelong climb up a series of increasingly steep mountains, as they are driven to put their noses to the grindstone, succeed, and earn recognition for their diligent, no-nonsense work. In fact, it is for this reason that they have a rep for overworking themselves. But they are also extremely loyal, have a gut-busting, hilarious, dry sense of humor, and have the ability to show you exactly what is possible when you commit to a pragmatic, steady, grounded approach. John Legend is a perfect example of an industrious double Cap (it is his sun and his rising/ascendant sign)."
    };

    int[] zodiacImages = {
            R.drawable.acquarius, R.drawable.pisces, R.drawable.aries,
            R.drawable.taurus, R.drawable.gemini, R.drawable.cancer,
            R.drawable.leo, R.drawable.virgo, R.drawable.libra,
            R.drawable.scorpio, R.drawable.sagittarius, R.drawable.capricorn
    };

    ListView lv;
    ItemAdapter adapter;
    ArrayList<Horoscope> list = new ArrayList<Horoscope>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = findViewById(R.id.listView);
        for (int i = 0; i < zodiacSigns.length; i++)
            list.add(new Horoscope(zodiacImages[i],zodiacSigns[i],dateRanges[i]));

        adapter = new ItemAdapter(this,list);
        lv.setAdapter(adapter);

//        ListView listView = findViewById(R.id.listView);
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, zodiacSigns);
//        listView.setAdapter(adapter);

        // Click listener for list items
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showHoroscopeDialog(position);
            }
        });
    }

    private void showHoroscopeDialog(int position) {
        // Create SweetAlertDialog with NORMAL_TYPE
        SweetAlertDialog dialog = new SweetAlertDialog(this, SweetAlertDialog.NORMAL_TYPE);

        // Inflate custom layout
        View customView = getLayoutInflater().inflate(R.layout.dialog_horoscope, null);

        // Initialize views
        ImageView zodiacImage = customView.findViewById(R.id.zodiacImage);
        TextView zodiacName = customView.findViewById(R.id.zodiacName);
        TextView dateRange = customView.findViewById(R.id.dateRange);
        TextView horoscopeText = customView.findViewById(R.id.horoscopeText);

        // Set data
        zodiacImage.setImageResource(zodiacImages[position]);
        zodiacName.setText(zodiacSigns[position]);
        dateRange.setText(dateRanges[position]);
        horoscopeText.setText(horoscopes[position]);

        // Configure dialog
        //dialog.setTitleText("");  // Remove default title
        dialog.setCustomView(customView);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setConfirmButton("OK", new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismissWithAnimation();
            }
        });

        dialog.show();
    }
}