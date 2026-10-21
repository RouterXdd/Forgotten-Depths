package ForgotDepths.content;

import ForgotDepths.classes.entities.abilities.ConstantStatusAbility;
import ForgotDepths.classes.entities.units.ScrapUnitType;
import ForgotDepths.graphics.FDPal;
import arc.Core;
import arc.graphics.Color;
import arc.math.Interp;
import arc.math.geom.Rect;
import mindustry.ai.UnitCommand;
import mindustry.ai.types.*;
import mindustry.content.Fx;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.*;
import mindustry.entities.part.RegionPart;
import mindustry.entities.pattern.ShootAlternate;
import mindustry.gen.*;
import mindustry.gen.Sounds;
import mindustry.graphics.Layer;
import mindustry.type.*;

import static ForgotDepths.FDMain.modName;
import static ForgotDepths.content.FDStatuses.jump;
import static mindustry.content.StatusEffects.*;


public class FDUnits {
    public static UnitType
    //player
    abandoned,
    //scrapyard
    armedBox, springling, roller, dreadnought;
    public static void load() {
        abandoned = new ScrapUnitType("abandoned"){{
            aiController = BuilderAI::new;
            defaultCommand = UnitCommand.rebuildCommand;
            isEnemy = false;

            lowAltitude = false;
            mineSpeed = 12f;
            mineTier = 1;
            buildSpeed = 0.5f;
            drag = 0.12f;
            speed = 1.75f;
            rotateSpeed = 8f;
            fogRadius = 8f;
            itemCapacity = 50;
            health = 340f;
            hitSize = 10f;
            constructor = LegsUnit::create;
            legLength = 15f;
            hovering = true;
            lockLegBase = true;
            legContinuousMove = false;
            legExtension = -3f;
            legBaseOffset = 4f;
            legMaxLength = 1f;
            legMinLength = 0.35f;
            legLengthScl = 0.95f;
            legForwardScl = 0.5f;

            legMoveSpace = 2.5f;
            groundLayer = Layer.legUnit - 1f;

            weapons.add(new Weapon(modName + "-abandoned-weapon"){{
                            top = true;
                            reload = 40f;
                            rotate = true;
                            shootCone = 20;
                            mirror = false;
                            x = 0f;
                            y = -3f;
                            shootY = 8.75f;
                            recoil = 0;

                            ejectEffect = Fx.none;

                            bullet = new BasicBulletType(4, 20){{
                                sprite = "large-bomb";
                                shrinkY = shrinkX = 0;
                                spin = 0.4f;
                                backColor = frontColor = trailColor = FDPal.playerColor;
                                height = width = 8;
                                trailWidth = 1.5f;
                                trailLength = 6;
                                homingPower = 0.025f;
                                collideTerrain = true;
                                lifetime = 35;
                                despawnEffect = hitEffect = new ParticleEffect(){{
                                    length = 25;
                                    cone = 25;
                                    lifetime = 19;
                                    particles = 3;
                                    region = "fd-blur-particle";
                                    sizeFrom = sizeTo = 3.5f;
                                    colorFrom = FDPal.playerColor;
                                    colorTo = Color.valueOf("0000ff00");
                                }};
                                shootEffect = new ParticleEffect(){{
                                    length = 0;
                                    lifetime = 20;
                                    region = "fd-blur-circle";
                                    particles = 1;
                                    sizeFrom = 0;
                                    sizeTo = 12;
                                    colorFrom = FDPal.playerColor;
                                    colorTo = Color.valueOf("0000ff00");
                                }};
                                smokeEffect = Fx.none;
                            }};
                        }});
            databaseTag = "uninfected";
        }};
        armedBox = new ScrapUnitType("armed-box"){{
            aiController = SuicideAI::new;

            speed = 0.02f;
            hitSize = 12f;
            health = 85;
            constructor = MechUnit::create;
            mechStepParticles = false;
            faceTarget = false;
            squareShape = true;
            range = 40f;
            mechSideSway = 0;
            mechFrontSway = 0;
            abilities.add(new ConstantStatusAbility(jump, 3.75f, 3.75f * 3));

            weapons.add(new Weapon(){{
                shootOnDeath = true;
                targetUnderBlocks = false;
                reload = 130f;
                shootCone = 360f;
                ejectEffect = Fx.none;
                shootSound = Sounds.explosionCrawler;
                shootSoundVolume = 0.4f;
                x = shootY = y = 0f;
                mirror = false;
                shootStatus = unmoving;
                shootStatusDuration = 95;
                bullet = new BulletType(){{
                    collidesTiles = false;
                    collides = false;
                    rangeOverride = 25f;
                    hitEffect = Fx.pulverize;
                    speed = 0f;
                    splashDamageRadius = 30f;
                    instantDisappear = true;
                    splashDamage = 80f;
                    buildingDamageMultiplier = 0.5f;
                    hittable = false;
                    collidesAir = true;

                    smokeEffect = despawnEffect = Fx.none;
                    shootEffect = new MultiEffect(
                            new ParticleEffect(){{
                                length = 0;
                                lifetime = 95;
                                region = "fd-blur-circle";
                                particles = 1;
                                sizeFrom = sizeTo = splashDamageRadius;
                                colorFrom = FDPal.infectedColor;
                                colorTo = Color.valueOf("ff000000");
                            }},
                            new ParticleEffect(){{
                                length = 0;
                                lifetime = 35;
                                region = "fd-blur-spike-circle";
                                particles = 1;
                                sizeFrom = 0;
                                sizeTo = splashDamageRadius;
                                colorFrom = FDPal.infectedColor;
                                colorTo = Color.valueOf("ff000000");
                            }},
                            new ParticleEffect(){{
                                length = 0;
                                lifetime = 35;
                                region = "fd-blur-spike-circle";
                                particles = 1;
                                sizeFrom = 0;
                                sizeTo = splashDamageRadius;
                                colorFrom = FDPal.infectedColor;
                                colorTo = Color.valueOf("ff000000");
                                startDelay = 32.5f;
                            }},
                            new ParticleEffect(){{
                                length = 0;
                                lifetime = 35;
                                region = "fd-blur-spike-circle";
                                particles = 1;
                                sizeFrom = 0;
                                sizeTo = splashDamageRadius;
                                colorFrom = FDPal.infectedColor;
                                colorTo = Color.valueOf("ff000000");
                                startDelay = 32.5f * 2;
                            }}

                    );
                }};
            }});
            databaseTag = "scrapyard";
        }
            @Override
            public void load() {
                super.load();
                legRegion = Core.atlas.find(modName + "-none");
                baseRegion = Core.atlas.find(modName + "-none");
            }
        };
        springling = new ScrapUnitType("springling"){{
            speed = 0.012f;
            rotateSpeed = 0.075f;
            drag = 0.24f;
            hitSize = 8f;
            health = 115;
            mechSideSway = 0;
            constructor = MechUnit::create;
            mechStepParticles = false;
            faceTarget = false;
            squareShape = true;
            abilities.add(new ConstantStatusAbility(jump, 5, 14));

            weapons.add(new Weapon(modName + "-springling-weapon"){{
                targetUnderBlocks = true;
                reload = 65f;
                shootCone = 40f;
                ejectEffect = Fx.none;
                shootSound = Sounds.shootDisperse;
                shootSoundVolume = 0.4f;
                rotateSpeed = 10;
                x = 0f;
                y = -1f;
                shootY = 21 / 4f;
                mirror = false;
                rotate = true;
                shoot.firstShotDelay = 25f;
                bullet = new BasicBulletType(5, 25){{
                    sprite = "large-bomb";
                    shrinkY = shrinkX = 0;
                    spin = 0.4f;
                    backColor = frontColor = trailColor = FDPal.infectedColor;
                    height = width = 6;
                    trailWidth = 1f;
                    trailLength = 3;
                    homingPower = 0.01f;
                    collideTerrain = true;
                    lifetime = 95 / 5f;
                    chargeEffect = new ParticleEffect(){{
                        length = 0;
                        lifetime = 25;
                        region = "fd-blur-circle";
                        particles = 1;
                        sizeFrom = 10;
                        sizeTo = 0;
                        colorFrom = Color.valueOf("FF000000");
                        colorTo = FDPal.infectedColor;
                    }};
                    despawnEffect = hitEffect = new ParticleEffect(){{
                        length = 18;
                        cone = 25;
                        lifetime = 15;
                        particles = 2;
                        region = "fd-blur-particle";
                        sizeFrom = sizeTo = 2.5f;
                        colorFrom = FDPal.infectedColor;
                        colorTo = Color.valueOf("FF000000");
                    }};
                    shootEffect = new ParticleEffect(){{
                        length = 20;
                        cone = 30;
                        lifetime = 20;
                        particles = 4;
                        region = "fd-blur-particle";
                        sizeFrom = sizeTo = 2.5f;
                        colorFrom = FDPal.infectedColor;
                        colorTo = Color.valueOf("FF000000");
                    }};
                    smokeEffect = Fx.none;
                }};
            }});
            databaseTag = "scrapyard";
        }
            @Override
            public void load() {
                super.load();
                legRegion = Core.atlas.find(modName + "-none");
            }
        };
        roller = new ScrapUnitType("roller"){{
            speed = 1.5f;
            accel = 0.2f;
            drag = 0.05f;
            hitSize = 19f;
            health = 330;
            constructor = TankUnit::create;
            omniMovement = false;
            faceTarget = true;
            treadRects = new Rect[]{new Rect(50 - 59f, 19 - 59f, 20, 80)};


            weapons.add(new Weapon(modName + "-roller-weapon"){{
                reload = 175f;
                shootCone = 360f;
                ejectEffect = Fx.none;
                shootSound = Sounds.shootArtillery;
                shootSoundVolume = 0.4f;
                x = 45 / 4f;
                shootY = 49 / 4f;
                y = 0f;
                mirror = true;
                rotate = true;
                top = false;
                rotationLimit = 15;
                rotateSpeed = 0.75f;
                bullet = new BasicBulletType(2.75f, 70){{
                    sprite = "circle";
                    shrinkY = shrinkX = 0;
                    backColor = frontColor = trailColor = FDPal.infectedColor;
                    height = width = 7;
                    trailWidth = 3f;
                    trailLength = 8;
                    homingPower = 0.03f;
                    collideTerrain = true;
                    lifetime = 65;
                    shootEffect = new MultiEffect(new ParticleEffect(){{
                        length = 38;
                        cone = 35;
                        lifetime = 20;
                        particles = 5;
                        region = "fd-blur-particle";
                        sizeFrom = sizeTo = 4f;
                        colorFrom = FDPal.infectedColor;
                        colorTo = Color.valueOf("FF000000");
                    }},
                    new ParticleEffect(){{
                        length = 0;
                        lifetime = 12;
                        region = "fd-blur-circle";
                        particles = 1;
                        sizeFrom = 0;
                        sizeTo = 20;
                        colorFrom = FDPal.infectedColor;
                        colorTo = Color.valueOf("FF000000");
                    }}
                    );
                    despawnEffect = hitEffect = new ParticleEffect(){{
                        length = 45;
                        cone = 40;
                        lifetime = 20;
                        particles = 8;
                        region = "fd-blur-particle";
                        sizeFrom = sizeTo = 5f;
                        colorFrom = FDPal.infectedColor;
                        colorTo = Color.valueOf("FF000000");
                    }};
                    smokeEffect = Fx.none;
                }};
            }});
            databaseTag = "scrapyard";
        }};
        dreadnought = new ScrapUnitType("dreadnought"){{
            speed = 0.3f;
            drag = 0.4f;
            hitSize = 26f;
            rotateSpeed = 1.4f;
            targetAir = false;
            faceTarget = false;
            health = 1500;
            constructor = LegsUnit::create;

            stepSound = Sounds.walkerStep;
            stepSoundPitch = 1f;
            stepSoundVolume = 0.4f;

            legCount = 4;
            legLength = 34f;
            legBaseOffset = 18;
            legForwardScl = 0.6f;
            legMoveSpace = 1.4f;
            legGroupSize = 1;
            legExtension = -2.55f;
            lockLegBase = true;
            hovering = true;
            armor = 8f;

            shadowElevation = 0.55f;
            groundLayer = Layer.legUnit;
            weapons.add(new Weapon(modName + "-dreadnought-weapon"){{
                rotate = true;
                mirror = false;
                shootY = 73f / 4;
                reload = 32f;
                ejectEffect = Fx.none;
                recoil = 2f;
                rotateSpeed = 0.95f;
                shootCone = 80;
                x = 0f;
                shoot = new ShootAlternate(2.5f);
                shoot.shots = 2;
                shootSound = Sounds.shootAtrax;
                bullet = new BasicBulletType(2.75f, 20){{
                    sprite = "circle";
                    shrinkY = shrinkX = 0;
                    backColor = frontColor = trailColor = FDPal.infectedColor;
                    height = 7;
                    width = 4;
                    trailWidth = 1.4f;
                    trailLength = 8;
                    homingPower = 0.03f;
                    collideTerrain = true;
                    lifetime = 90;
                    despawnEffect = hitEffect = new ParticleEffect(){{
                        length = 36;
                        cone = 25;
                        lifetime = 24;
                        particles = 4;
                        region = "fd-blur-particle";
                        sizeFrom = sizeTo = 3.25f;
                        colorFrom = FDPal.infectedColor;
                        colorTo = Color.valueOf("FF000000");
                    }};
                    smokeEffect = Fx.none;
                    shootEffect = new ParticleEffect(){{
                        length = 0;
                        lifetime = 28;
                        region = "fd-blur-circle";
                        particles = 1;
                        sizeFrom = 0;
                        sizeTo = 28;
                        colorFrom = FDPal.infectedColor;
                        colorTo = Color.valueOf("FF000000");
                    }};
                    trailEffect = new ParticleEffect(){{
                        length = 20;
                        cone = 20;
                        lifetime = 16.5f;
                        particles = 3;
                        region = "fd-blur-particle";
                        sizeFrom = sizeTo = 4.5f;
                        colorFrom = FDPal.infectedColor;
                        colorTo = Color.valueOf("FF000000");
                    }};
                    trailRotation = true;
                    trailInterval = 3f;
                    smokeEffect = Fx.none;
                }};
                parts.add(new RegionPart("-charge"){{
                    progress = PartProgress.reload;
                    colorTo = Color.black;
                    color = FDPal.infectedColor;
                }});
            }});
            databaseTag = "scrapyard";
        }};
    }
}
