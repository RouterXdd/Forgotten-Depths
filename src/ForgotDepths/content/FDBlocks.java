package ForgotDepths.content;

import ForgotDepths.classes.blocks.defence.PowerAmmoTurret;
import ForgotDepths.classes.blocks.environment.*;
import ForgotDepths.classes.blocks.production.WretchingDrill;
import ForgotDepths.classes.blocks.storage.ScrapCore;
import ForgotDepths.classes.campain.DisabledUnit;
import ForgotDepths.graphics.FDPal;
import arc.graphics.Color;
import arc.math.Interp;
import mindustry.content.Blocks;
import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.content.StatusEffects;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.*;
import mindustry.entities.part.RegionPart;
import mindustry.gen.Sounds;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.type.Category;
import mindustry.world.*;
import mindustry.world.blocks.defense.Wall;
import mindustry.world.blocks.distribution.*;
import mindustry.world.blocks.environment.*;
import mindustry.world.blocks.production.GenericCrafter;
import mindustry.world.draw.*;
import mindustry.world.meta.BuildVisibility;

import static ForgotDepths.content.FDFX.*;
import static ForgotDepths.content.FDItems.*;
import static ForgotDepths.content.FDUnits.*;
import static mindustry.type.ItemStack.*;

public class FDBlocks {
    public static Block
        //env
            //General
            pit,
            //Scrapyard
            oldScrapFloor, oldScrapWall, oldScrapBridge, oldScrapOre, scrapPorp,
            //Hazards
            scraplash, disabledDreadnought,
        //crafting
        pressurizer,
        //distribution
        scrapShifter, scrapRouter, scrapOverpass,
        //production
        scrapper,
        //storage
        scrapCore,
        //defence
        trueScrapWall, largeTrueScrapWall,
        turret, ranger
            ;
    public static void load() {
        //env
        pit = new Floor("pit", 0){{
            isLiquid = true;
            drownTime = 5;
            drownUpdateEffect = walkEffect = Fx.none;
            walkSound = Sounds.none;
        }
        @Override
        public void init(){
            super.init();
            walkEffect = Fx.none;
            walkSound = Sounds.none;
        }
        };

        //env scrap
        oldScrapFloor = new Floor("old-scrap-floor", 3);
        oldScrapWall = new StaticWall("old-scrap-wall");
        oldScrapBridge = new BitmaskedFloor("old-scrap-bridge", 0){{
            drawEdgeIn = drawEdgeOut = false;
            autotile = false;
            autotileVariants = 1;
        }};
        oldScrapOre = new ShineOre(oldScrap){{
            effectChance = 0.0008f;
            effect = new WaveEffect(){{
                sizeFrom = 0;
                sizeTo = 5;
                colorTo = Color.valueOf("ffffff00");
                lifetime = 22;
                lightScl = 1;
            }};
        }};
        scrapPorp = new Prop("scrap-prop"){{
            variants = 2;
            oldScrapFloor.asFloor().decoration = this;
        }};

        //hazard
        scraplash = new PowerAmmoTurret("scraplash"){{
            requirements(Category.logic, BuildVisibility.sandboxOnly, with());
            shootType = new BasicBulletType(9, 65){{
                sprite = "large-bomb";
                shrinkY = shrinkX = 0;
                spin = 0.4f;
                backColor = frontColor = trailColor = FDPal.infectedColor;
                height = width = 10;
                trailWidth = 1.5f;
                trailLength = 10;
                collideTerrain = true;
                homingPower = 0.025f;
                lifetime = 320 / 9f;
                chargeEffect = new MultiEffect(new ParticleEffect(){{
                    length = 0;
                    lifetime = 80;
                    region = "fd-blur-circle";
                    particles = 1;
                    sizeFrom = 15;
                    sizeTo = 0;
                    colorFrom = Color.valueOf("FF000000");
                    colorTo = FDPal.infectedColor;
                }}, new ParticleEffect(){{
                    length = 45;
                    lifetime = 16;
                    region = "fd-blur-particle";
                    particles = 6;
                    cone = 35;
                    sizeFrom = 7;
                    sizeTo = 0;
                    interp = Interp.reverse;
                    colorTo = Color.valueOf("FF000000");
                    colorFrom = FDPal.infectedColor;
                    startDelay = 0;
                }}, new ParticleEffect(){{
                    length = 45;
                    lifetime = 16;
                    region = "fd-blur-particle";
                    particles = 6;
                    cone = 35;
                    sizeFrom = 7;
                    sizeTo = 0;
                    interp = Interp.reverse;
                    colorTo = Color.valueOf("FF000000");
                    colorFrom = FDPal.infectedColor;
                    startDelay = 16;
                }},new ParticleEffect(){{
                    length = 45;
                    lifetime = 16;
                    region = "fd-blur-particle";
                    particles = 6;
                    cone = 35;
                    sizeFrom = 7;
                    sizeTo = 0;
                    interp = Interp.reverse;
                    colorTo = Color.valueOf("FF000000");
                    colorFrom = FDPal.infectedColor;
                    startDelay = 32;
                }},new ParticleEffect(){{
                    length = 45;
                    lifetime = 16;
                    region = "fd-blur-particle";
                    particles = 6;
                    cone = 35;
                    sizeFrom = 7;
                    sizeTo = 0;
                    interp = Interp.reverse;
                    colorTo = Color.valueOf("FF000000");
                    colorFrom = FDPal.infectedColor;
                    startDelay = 48;
                }},new ParticleEffect(){{
                    length = 45;
                    lifetime = 16;
                    region = "fd-blur-particle";
                    particles = 6;
                    cone = 35;
                    sizeFrom = 7;
                    sizeTo = 0;
                    interp = Interp.reverse;
                    colorTo = Color.valueOf("FF000000");
                    colorFrom = FDPal.infectedColor;
                    startDelay = 64;
                }});
                despawnEffect = hitEffect = new ParticleEffect(){{
                    length = 40;
                    cone = 20;
                    lifetime = 25;
                    particles = 5;
                    region = "fd-blur-particle";
                    sizeFrom = sizeTo = 3f;
                    colorFrom = FDPal.infectedColor;
                    colorTo = Color.valueOf("FF000000");
                }};
                smokeEffect = Fx.none;
            }};
            shoot.firstShotDelay = 80f;
            moveWhileCharging = predictTarget = drawTeamOverlay = false;
            outlineColor = Color.valueOf("171719");
            researchCostMultiplier = 1/4f;
            reload = 120f;
            shootCone = 15f;
            rotateSpeed = 5f;
            range = 320f;
            shootEffect = new MultiEffect(new ParticleEffect(){{
                length = 0;
                lifetime = 20;
                region = "fd-blur-circle";
                offsetX = -15.5f;
                particles = 1;
                sizeFrom = 0;
                sizeTo = 20;
                colorFrom = FDPal.infectedColor;
                colorTo = Color.valueOf("ff000000");
            }});
            shootY = 15.5f;
            heatColor = Color.red;
            recoil = 1f;
            size = 2;
            health = 420;
            reloadTime = 20 * 60f;
            noBuildRange = 0f * 8;
            maxAmmo = 0;
            shootSound = Sounds.shootBreachCarbide;
            connectedPower = false;
            squareSprite = false;
            outlineRadius = 0;
            drawer = new DrawTurret("scrap-"){{
                parts.add(new RegionPart("-glow"){{
                    progress = PartProgress.reload;
                    colorTo = Color.black;
                    color = FDPal.infectedColor;
                }});
            }};

        }};
        disabledDreadnought = new DisabledUnit("disabled-dreadnought"){{
            requirements(Category.logic, BuildVisibility.sandboxOnly, with());
            health = 2000;
            size = 4;
            activateEffect = new MultiEffect(
                new ParticleEffect(){{
                    length = 0;
                    lifetime = 25;
                    region = "fd-blur-circle";
                    particles = 1;
                    sizeFrom = 0;
                    sizeTo = 50;
                    colorFrom = FDPal.infectedColor;
                    colorTo = Color.valueOf("FF000000");
                }},
                new ParticleEffect(){{
                    length = 0;
                    lifetime = 35;
                    region = "fd-blur-flare";
                    particles = 1;
                    spin = 2;
                    sizeFrom = 0;
                    sizeTo = 50;
                    colorFrom = FDPal.infectedColor;
                    colorTo = Color.valueOf("FF000000");
                }},
                new ParticleEffect(){{
                    length = 0;
                    lifetime = 75;
                    region = "fd-dreadnought-lights";
                    particles = 1;
                    sizeFrom = sizeTo = 20;
                    colorFrom = colorTo = FDPal.infectedColor;
                }}
            );
        }};

        //crafting
        pressurizer = new GenericCrafter("pressurizer"){{
            requirements(Category.crafting, with(oldScrap, 140));
            craftTime = 120f;
            size = 3;
            consumeItems(with(oldScrap, 2));

            outputItems = with(scrapPlate, 1);

            drawer = new DrawMulti(new DrawRegion("-bottom"), new DrawRegion("-piston-end"), new DrawPistons(){{
                sides = 4;
                lenOffset = 0f;
                sinScl = 15f;
                sinMag = 4;
            }}, new DrawDefault());
            researchCostMultiplier = 0.35f;
        }};

        //distribution
        scrapShifter = new ArmoredConveyor("scrap-shifter"){{
            requirements(Category.distribution, with(oldScrap, 2));
            health = 60;
            speed = 0.032f;
            displayedSpeed = 5f;
            buildCostMultiplier = 0.75f;
            researchCost = with(oldScrap, 20);
        }};
        scrapRouter = new Router("scrap-router"){{
            requirements(Category.distribution, with(oldScrap, 6));
            speed = 7;
            researchCost = with(oldScrap, 60);
        }};
        scrapOverpass = new BufferedItemBridge("scrap-overpass"){{
            requirements(Category.distribution, with(oldScrap, 10));
            fadeIn = moveArrows = false;
            range = 2;
            speed = 55f;
            arrowSpacing = 6f;
            bufferCapacity = 5;
            crushFragile = true;
            bridgeWidth = 8;
            arrowOffset = 4;
            researchCost = with(oldScrap, 100);
            buildCostMultiplier = 0.75f;
        }};
        ((ArmoredConveyor) scrapShifter).bridgeReplacement = scrapOverpass;

        //production
        scrapper = new WretchingDrill("scrapper"){{
            requirements(Category.production, with(oldScrap, 35));
            health = 200;
            size = 2;
            tier = 2;
            drillTime = 180;
            wretchedTimeMin = 12 * 60;
            wretchedTimeMax = 25 * 60;
            wretchedRechargeMin = 9 * 60;
            wretchedRechargeMax = 16f * 60;
            researchCost = with(oldScrap, 40);
            wretchEffect = new ParticleEffect(){{
                colorFrom = Pal.yellowBoltFront;
                lifetime = 35;
                particles = 2;
                cone = 360;
                line = true;
                lenFrom = 4;
                lenTo = 0;
                length = 14;
                baseLength = 6;
                strokeFrom = strokeTo = 1.25f;
            }};
        }};

        //storage
        scrapCore = new ScrapCore("scrapped-core"){{
                requirements(Category.effect, with(oldScrap, 1000));
                alwaysUnlocked = true;

                isFirstTier = true;
                unitType = abandoned;
                health = 1200;
                itemCapacity = 2500;
                squareSprite = false;
                incinerateNonBuildable = true;
                size = 3;

                unitCapModifier = 10;
                drawTeamOverlay = false;
        }};

        //defence
        trueScrapWall = new Wall("true-scrap-wall"){{
            requirements(Category.defense, with(scrapPlate, 6));
            health = 300;
            buildCostMultiplier = 2.25f;
            flashHit = true;
        }};
        largeTrueScrapWall = new Wall("large-true-scrap-wall"){{
            requirements(Category.defense, with(scrapPlate, 24));
            health = 1200;
            armor = -20;
            buildCostMultiplier = 2.25f;
            size = 2;
            flashHit = true;
        }};

        //defence turrets
        turret = new PowerAmmoTurret("turret"){{
            requirements(Category.turret, with(oldScrap, 80));
            shootType = new BasicBulletType(4, 10){{
                sprite = "large-bomb";
                shrinkY = shrinkX = 0;
                spin = 0.4f;
                backColor = frontColor = trailColor = FDPal.playerColor;
                height = width = 6;
                trailWidth = 1f;
                trailLength = 3;
                homingPower = 0.01f;
                collideTerrain = true;
                lifetime = 110 / 4f;
                despawnEffect = hitEffect = new ParticleEffect(){{
                    length = 20;
                    cone = 25;
                    lifetime = 15;
                    particles = 3;
                    region = "fd-blur-particle";
                    sizeFrom = sizeTo = 3f;
                    colorFrom = FDPal.playerColor;
                    colorTo = Color.valueOf("0000ff00");
                }};
            }};
            outlineColor = Color.valueOf("171719");
            researchCostMultiplier = 1/4f;
            reload = 35f;
            shootCone = 40f;
            rotateSpeed = 6.5f;
            range = 110f;
            shootEffect = new MultiEffect(new ParticleEffect(){{
                length = 0;
                lifetime = 20;
                region = "fd-blur-circle";
                particles = 1;
                sizeFrom = 0;
                sizeTo = 10;
                colorFrom = FDPal.playerColor;
                colorTo = Color.valueOf("0000ff00");
            }},new ParticleEffect(){{
                length = 24;
                cone = 25;
                lifetime = 20;
                particles = 5;
                region = "fd-blur-particle";
                sizeFrom = sizeTo = 3f;
                colorFrom = FDPal.playerColor;
                colorTo = Color.valueOf("0000ff00");
            }});
            smokeEffect = Fx.none;
            heatColor = Color.red;
            recoil = 1f;
            size = 2;
            health = 270;
            shootSound = Sounds.shootDisperse;
            connectedPower = false;
            squareSprite = false;
            researchCost = with(oldScrap, 300);
        }};
        ranger = new PowerAmmoTurret("ranger"){{
            requirements(Category.turret, with(oldScrap, 110, scrapPlate, 10));
            shootType = new BasicBulletType(9, 40){{
                sprite = "large-bomb";
                shrinkY = shrinkX = 0;
                spin = 0.4f;
                backColor = frontColor = trailColor = FDPal.playerColor;
                height = width = 7;
                trailWidth = 1.2f;
                trailLength = 7;
                homingPower = 0.025f;
                collideTerrain = true;
                lifetime = 240 / 9f;
                despawnEffect = hitEffect = new ParticleEffect(){{
                    length = 30;
                    cone = 30;
                    lifetime = 18;
                    particles = 5;
                    region = "fd-blur-particle";
                    sizeFrom = sizeTo = 4f;
                    colorFrom = FDPal.playerColor;
                    colorTo = Color.valueOf("0000ff00");
                }};
            }};
            outlineColor = Color.valueOf("171719");
            researchCostMultiplier = 1/4f;
            reload = 110f;
            shootCone = 15f;
            rotateSpeed = 5f;
            range = 240f;
            shootEffect = new MultiEffect(new ParticleEffect(){{
                length = 0;
                lifetime = 12;
                region = "fd-blur-circle";
                offsetX = -12;
                particles = 1;
                sizeFrom = 0;
                sizeTo = 12;
                colorFrom = FDPal.playerColor;
                colorTo = Color.valueOf("0000ff00");
            }},new ParticleEffect(){{
                length = 32;
                cone = 40;
                lifetime = 25;
                particles = 8;
                region = "fd-blur-particle";
                sizeFrom = sizeTo = 4f;
                colorFrom = FDPal.playerColor;
                colorTo = Color.valueOf("0000ff00");
            }},new ParticleEffect(){{
                length = 0;
                lifetime = 12;
                region = "fd-blur-spike-circle";
                particles = 1;
                sizeFrom = 0;
                sizeTo = 15;
                colorFrom = FDPal.playerColor;
                colorTo = Color.valueOf("0000ff00");
            }});
            smokeEffect = Fx.none;
            shootY = 12;
            heatColor = Color.red;
            recoil = 1f;
            size = 2;
            health = 210;
            reloadTime = 16 * 60f;
            noBuildRange = 5f * 8;
            maxAmmo = 3;
            shootSound = Sounds.shootBreachCarbide;
            connectedPower = false;
            squareSprite = false;
            outlineRadius = 0;
            drawer = new DrawTurret("scrap-"){{
                parts.add(new RegionPart("-glow"){{
                    progress = PartProgress.reload;
                    colorTo = Color.black;
                    color = FDPal.playerColor;
                }});
            }};

        }};
    }
}
