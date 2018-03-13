import java.util.HashSet;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        class Container {
            int x;
            int y;
            double value;

            public double getValue() {
                return value;
            }
        }

        List<DirtyStock> titres = Parser.parseDirtyStocks("../data/data.csv", "../data/stocks.csv");

        if (titres.size() != 100) {
            System.out.println("Erreur. Nombre de titres différents de 100.");
        }

        //LocalDate debut = LocalDate.of(1983, 01, 01);
        long debut = 1983*12+1;
        //LocalDate fin = LocalDate.of(2005, 01, 01);
        long fin = 2005*12+1;

        double produitMarche=1;
        for (long i = debut; i < fin; i++) {
            double sousMoyenneAri=0;
            for (DirtyStock titre : titres) {
                sousMoyenneAri += (1+titre.getYields().get(i));
            }
            produitMarche *= (1 + sousMoyenneAri/100.0);
        }
        System.out.println("Rendement mensuel géométrique du marché : "+( Math.pow(produitMarche, 1.0/(fin-debut)) -1) );

        double sommeMarche=0;
        double sommeMarcheCarres=0;
        for (long i = debut; i < fin; i++) {
            double sousMoyenneAri=0;
            for (DirtyStock titre : titres) {
                sousMoyenneAri += (1+titre.getYields().get(i));
            }
            sommeMarche += (sousMoyenneAri/100.0);
            sommeMarcheCarres += Math.pow((sousMoyenneAri/100.0),2);
        }
        System.out.println("Rendement mensuel arithmétique du marché : "
                + ( sommeMarche/(fin-debut) ));
        System.out.println("Ecart-type mensuel arithmétique du marché : "
                + ( sommeMarcheCarres/(fin-debut) - sommeMarche*sommeMarche/(fin-debut)/(fin-debut) ) );

        // programme

        Utils.PortfoliosYields per;

        int PID = 9; // 0 = P1 ; 9 = P10
        boolean P10P1 = false;
        double TSRmensuel = 0.0041; // mensuel, arithmétique

        int xMax = 0;
        int yMax = 0;
        double currentMax = 0;
        double yield = 0;
        Container container;
        double[][] results = new double[18][18];
        double[][] resultsTransaction = new double[18][18];
        double[][] resultsWithoutTransaction = new double[18][18];
        double[][] betas = new double[18][18];
        double[][] moyenneAri = new double[18][18];
        double[][] ecartsTypesAri = new double[18][18];
        double[][] ecartsTypes = new double[18][18];
        HashSet<Container> resultsSet = new HashSet<Container>();
        for (int x=1; x<=18; x++) { //18
            for (int y=1; y<=18; y++) { //18
                long date = debut;
                date=date+x;
                int nb = 0;
                double produit = P10P1 ? 0 : 1;
                double transactionLosses = P10P1 ? 0 : 1;
                double transactionCost = 1;
                double somme = 0;
                double sommeCarres = 0;
                double sommeLnCarres = 0;
                double sommeBetas = 0;
                per=null;
                List<DirtyStock> formerList;
                List<DirtyStock> formerList2;
                List<DirtyStock> newList;
                while (date+y < fin) {

                    if (P10P1) {
                        formerList = (per==null ? null : per.getPortfolio(0));
                        formerList2 = (per==null ? null : per.getPortfolio(9));
                        per = Utils.extractP1toP10(titres, date-x, x);
                        if (formerList!=null) {
                            transactionLosses += 1-Utils.transactionCosts(formerList, per.getPortfolio(0));
                            transactionLosses += 1-Utils.transactionCosts(formerList2, per.getPortfolio(9));
                        }
                    }
                    else {
                        formerList = (per==null ? null : per.getPortfolio(PID)); // faux pour P10-P1
                        per = Utils.extractP1toP10(titres, date-x, x);
                        newList = per.getPortfolio(PID); // faux pour P10-P1

                        if (formerList!=null) {
                            transactionCost = Utils.transactionCosts(formerList, newList);
                            transactionLosses *= transactionCost;
                        }
                    }
                    // yield=Math.pow(( 2*(Math.pow(1+TSRannuel, y/12.0)) - Math.pow(Utils.portfolioYield(per.getPortfolio(PID), date, y), y/12.0) ), 12.0/y);
                    if (P10P1) {
                        yield = Utils.portfolioYield(per.getPortfolio(9), date, y) - Utils.portfolioYield(per.getPortfolio(0), date, y);
                    }
                    else {
                        yield = Utils.portfolioYield(per.getPortfolio(PID), date, y);
                    }

                    if (P10P1) {
                        produit+=yield;
                        somme+=yield;
                        sommeCarres+=(yield)*(yield);
                        sommeLnCarres+=Math.log(yield)*Math.log(yield); // inutilisé
                        sommeBetas += Utils.portfolioBeta(per.getPortfolio(9), date, y) - Utils.portfolioBeta(per.getPortfolio(0), date, y);
                    }
                    else {
                        produit*=yield;
                        somme+=yield-1;
                        sommeCarres+=(yield-1)*(yield-1);
                        sommeLnCarres+=Math.log(yield)*Math.log(yield);
                        sommeBetas += Utils.portfolioBeta(per.getPortfolio(PID), date, y);
                    }

                    ++nb;
                    date += y;
                }
                if (P10P1) {
                    System.out.println("Rebalancement : "+x+" mois. Attente : "+y+" mois. Performance : "+(produit/nb - transactionLosses/nb/y));
                }
                else {
                    System.out.println("Rebalancement : "+x+" mois. Attente : "+y+" mois. Performance : "+Math.pow(produit, 1.0/nb)*Math.pow(Math.pow(transactionLosses, 1.0/nb), 1.0/y));
                }

                System.out.println();

                if (P10P1) {
                    results[y-1][x-1] = ((produit/nb - transactionLosses/nb/y));
                    resultsTransaction[y-1][x-1] = - transactionLosses/nb/y;
                    resultsWithoutTransaction[y-1][x-1] = produit/nb;
                    betas[y-1][x-1] = sommeBetas/nb;
                    moyenneAri[y-1][x-1] = somme/nb;
                    //ecartsTypes[y-1][x-1] = Math.exp(  Math.sqrt( sommeLnCarres/nb ) - Math.pow(Math.log( produit/nb ) , 2) ); //Geométriques !
                    ecartsTypesAri[y-1][x-1] = Math.sqrt( (sommeCarres/nb) - (somme*somme/nb/nb) )*Math.sqrt(y);
                }
                else {
                    results[y-1][x-1] = (Math.pow(produit, 1.0/nb)*Math.pow(Math.pow(transactionLosses, 1.0/nb), 1.0/y))-1;
                    resultsTransaction[y-1][x-1] = Math.pow(Math.pow(transactionLosses, 1.0/nb), 1.0/y);
                    resultsWithoutTransaction[y-1][x-1] = Math.pow(produit, 1.0/nb)-1;
                    betas[y-1][x-1] = sommeBetas / nb;
                    moyenneAri[y-1][x-1] = somme / nb;
                    //ecartsTypes[y-1][x-1] = Math.exp(  Math.sqrt( sommeLnCarres/nb ) - Math.pow(Math.log( Math.pow(produit, 1.0/nb) ) , 2) ); //Geométriques !
                    ecartsTypesAri[y-1][x-1] = (  Math.sqrt( (sommeCarres/nb) - (somme*somme/nb/nb) )  )*Math.sqrt(y);
                }

                container = new Container();
                container.x = x;
                container.y = y;
                container.value = Math.pow(produit, 1.0/nb)*Math.pow(Math.pow(transactionLosses, 1.0/nb), 12.0/y);
                resultsSet.add(container);
                if (Math.pow(produit, 1.0/nb)*Math.pow(Math.pow(transactionLosses, 1.0/nb), 12.0/y) > currentMax) {
                    xMax=x;
                    yMax=y;
                    currentMax=Math.pow(produit, 1.0/nb)*Math.pow(Math.pow(transactionLosses, 1.0/nb), 12.0/y);
                }
            }
        }

        System.out.println();
        System.out.println("Paramètres optimaux :");
        System.out.println(xMax+" mois de rebalancement.");
        System.out.println(yMax+" mois d'attente.");
        System.out.println("Performance : "+currentMax);

        System.out.println();
        for (int i = 0 ; i < 18; ++i) { //y
            for (int j = 0 ; j < 18 ; ++j) { //x
                double result = resultsWithoutTransaction[i][j];
                if (result>=0) { System.out.print("+"); }
                System.out.printf("%.5f",result);
                System.out.print("\t");
                System.out.print(" ");
            }
            System.out.println();
        }

        System.out.println("Rendements moyen (avec frais)");
        System.out.println();
        for (int i = 0 ; i < 18; ++i) {
            for (int j = 0 ; j < 18 ; ++j) {
                double result = results[i][j];
                if (result>=0) { System.out.print("+"); }
                System.out.printf("%.5f",result);
                System.out.print("\t");
                System.out.print(" ");
            }
            System.out.println();
        }

        System.out.println();
        for (int i = 0 ; i < 18; ++i) {
            for (int j = 0 ; j < 18 ; ++j) {
                double result = resultsTransaction[i][j];
                System.out.printf("%.5f",result);
                System.out.print("\t");
            }
            System.out.println();
        }

        System.out.println();
        System.out.println("Betas de la stratégie");
        for (int i = 0 ; i < 18; ++i) {
            for (int j = 0 ; j < 18 ; ++j) {
                double result = betas[i][j];
                System.out.printf("%.5f",result);
                System.out.print("\t");
            }
            System.out.println();
        }

		/*System.out.println();
		System.out.println("Ecarts-types géométriques");
		for (int i = 0 ; i < 18; ++i) {
			for (int j = 0 ; j < 18 ; ++j) {
				double result = (double)((int)(ecartsTypes[i][j]*1000))/1000.0;
				System.out.print(result);
				System.out.print("\t");
				System.out.print("  ");
			}
			System.out.println();
		}*/

        System.out.println();
        System.out.println("Moyennes arithmétiques");
        for (int i = 0 ; i < 18; i++) {
            for (int j = 0 ; j < 18 ; j++) {
                double result = moyenneAri[i][j];
                if (result >= 0) { System.out.print(" "); }
                System.out.printf("%.5f",result);
                System.out.print("\t");
                System.out.print(" ");
            }
            System.out.println();
        }

        System.out.println();
        System.out.println("Ecarts-types arithmétiques");
        for (int i = 0 ; i < 18; i++) {
            for (int j = 0 ; j < 18 ; j++) {
                double result = ecartsTypesAri[i][j];
                System.out.printf("%.5f",result);
                System.out.print("\t");
                System.out.print("  ");
            }
            System.out.println();
        }

        System.out.println();
        System.out.println("Ratios de Sharpe");
        for (int i = 0 ; i < 18; i++) {
            for (int j = 0 ; j < 18 ; j++) {
                double result = (!P10P1) ?
                        (moyenneAri[i][j]-TSRmensuel)/ecartsTypesAri[i][j] :
                        (moyenneAri[i][j])/ecartsTypesAri[i][j];
                if (result>=0) { System.out.print(" "); }
                System.out.printf("%.4f",result);
                if (result > -10) { System.out.print(" "); }
                System.out.print("\t");
            }
            System.out.println();
        }

        System.out.println();
        System.out.println("Ratios de Roy");
        for (int i = 0 ; i < 18; ++i) {
            for (int j = 0 ; j < 18 ; ++j) {
                double result = (moyenneAri[i][j]-TSRmensuel-(0.002))/ecartsTypesAri[i][j];
                if (result>=0) { System.out.print(" "); }
                System.out.printf("%.5f",result);
                if (result > -10) { System.out.print(" "); }
                System.out.print("\t");
            }
            System.out.println();
        }

        System.out.println();
        System.out.println("Ratios de Treynor");
        for (int i = 0 ; i < 18; ++i) {
            for (int j = 0 ; j < 18 ; ++j) {
                double result = (moyenneAri[i][j]-TSRmensuel)/betas[i][j];
                if (result>=0) { System.out.print(" "); }
                System.out.printf("%.5f",result);
                System.out.print("\t");
            }
            System.out.println();
        }

        System.out.println();
        System.out.println("Alpha de Jensen");
        for (int i = 0 ; i < 18; ++i) {
            for (int j = 0 ; j < 18 ; ++j) {
                double result = moyenneAri[i][j] - (TSRmensuel + betas[i][j]*(0.011 - TSRmensuel) );
                if (result>=0) { System.out.print(" "); }
                System.out.printf("%.5f",result);
                System.out.print("\t");
            }
            System.out.println();
        }
    }
}