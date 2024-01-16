package ua.javarush.processor;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.javarush.game.Game;

import java.io.IOException;
import java.io.InputStream;

public class GameProcessor {
    private static final int POSITIVE_ANSWER_INDEX = 0;
    private static final int NEGATIVE_ANSWER_INDEX = 1;
    private static final int FAiL_ANSWER_INDEX = -1;
    private static final int MAX_NUMBER_OF_QUESTIONS = 2;
    private static final String VICTORY_MESSAGE = "You won!";
    private static final String FAILURE_MESSAGE = "Failure";
    private static final String VICTORY_IMG = "data:image/jpeg;base64,/9j/4AAQSkZJRgABAQAAAQABAAD/2wCEAAkGBxISEhUQEhIWFRUXGBUVFxUVFxcYGBgWFxUXFhcVFRYYHSggGBolGxgVITEhJSorLi4uFx8zODMsNygtLisBCgoKDg0OGhAQGy8lICYtLS8tLS0tLTAtLS0tLS0tLS0vLS0tLS0tLS0tLy0tLS0tLS0tLS0tLS0tLS0tLS0tLf/AABEIAOgA2QMBIgACEQEDEQH/xAAcAAABBAMBAAAAAAAAAAAAAAAAAwQFBwECBgj/xABOEAACAAQDAwgDCQ0GBwEAAAABAgADBBESITEFBkEHEyJRYXGBkRQyoRYjQlRygrHB0RUzQ1JTYoOSk6Ky0vAXNESzwvFjc3TD0+HiJP/EABsBAAIDAQEBAAAAAAAAAAAAAAAEAQIDBQYH/8QAPBEAAQMBBQQHBgMIAwAAAAAAAQACEQMEEiExQQVRcZETImGBobHRBhQyQlLBcuHwFRYjMzSCkvFTorL/2gAMAwEAAhEDEQA/ALxggggQiCCCBC1w53jaCCBCIIbVlWkpccxgo7ePYBxMRIr58/7wmCX+VfU9oX/fwjRlJzhOQ3nAfrgsaldjDdzduGJ/IdpgdqmJ9QiDE7BR1k2iLbb6tlIR5x7AVXxY6eUNZ9FTyTjnuZ03WxzJ+YOHflEbtDeR/UkgSwMsgL+HV5eMM0bOHnqgu7T1R6nwSNotrqQ65DewdZ32aDzCfVCVTXd2lU4JB6ZDEd2djDKdtMD1qybMPVLUSx7SPoiBmzGY4mYseskk+ZjWOlTsbW5x3AeZk+S4tbaT3fCD3uPk0tb4FP6ivVtFc9sybi9lhDFmvwt3X+sxiCGmsAy+/wB0g+q5/wAXkPslFnsNCR3XESNHXzteebsFzxF727s+3OGNKQDmbaa6W1P0CNJs4nLh3DPO+ZjN7A43YHHBbUqhpi9ePAH0P5cVMjeecttH67jI+It7R9sSdJvZLOUxSnd0vqB9kcfBGbrDRcMuX6jwWzNqWphm9PYRP5+KsylrJc0XluG7jmO8aiHUVUjkG4JBGhBsfAiJ7Z29E1LCaMa9ehHjp5+cIVdmvbiwz5+nkutZ9tU3YVRd7RiPUeK7eCGVBtCVOF5bX6xoR3iHsc4gtMHNdlrmvF5pkbwmNRRlmLYurK0PoIIhWRBBBAhEEaO0a4j1/wBeUCErDJa27YMPG17+3SHgMIrTIDiAz11MCEvBBBAhEQW2NvpKPNpZ5mmZ6Kn849fZ9EKVO01M30bNcQ9fgb6hbZ9efYYha3k9obFk52QdS0mawue5riNKd1pDniQRIg59/wDpL1S+owii4Agxlkd2kHuO9LNsvnPfnrFLjU2VkXjYAnQQhN2zPW4M7Eugfm1U99joNR3iI5d2JssES6pnz/DKHYqdVxjTgb2yI7bQwqtl7SvkKeYOFiysR+cSAL92UONr0nH+JlpLRhwj8u9c6pZK7RNIQdYe7E5YyQO/E9oSs+eWubk31JN753uL5652hCGzJXL69Cx7ZcxG9ghBtoTF9ejql7eZuPO8dFlrs+Qd5rj1Nn2uZLD3QfupCCIxtuyh6wmr8qWR9UajeGl/K+at9kaC00j8w5hYGx2gZ03f4lSsFojRt2n/ACye37I3+7dN+WX2/ZFumpn5hzCr7tW+h3+J9E/giNO3af8ALJ+99kaneCl/Kj9VvsgNel9Q5hAstc5U3f4n0UpBEWu3pJ9XnG+TLY/VCg2kx9Slq27pJt53ihtVEfMOa0FhtJypu5R5qQhGZNtfK9u3PvtbSE19Mb1KGZ+kdE+mN12JtJ8+bp5d9ccyY38GRjJ1vo6O8D6Jinsq0k9ZniPUpzKmsjBlJVhoRkY63Y28oeyTrK3BtAflfin2d0ctL3Tqm++VgUdUmUPYzG/shxK3HpvwsydO/wCbNNvJbQlabXRrCLpnQ4D15YrpWLZ9qs7pvgDUYkHyx7QeOGCsiCOdoq9JBlUwU4fVUC5wKNNc7fQB2R0JMc4giJ1x7sRPgeS7TXtcSAcjB4wD5ELMJs3ARhmvGypEKywiRvaMwQIWAIzBBAhER9bU/AXxP1QtWTSq5a9fVEXaPPba2gaY6FmEjE9m4cdSMkzQpXsSozbIsZbjUNbwOf1e2JaoqC5z4aRBVbGfNCSyLS/hai/9D6YVxVK6hZndZfsju7Nsr2WGjTeQHAEwTBAc4uAxwGBmJwlcg2ge8VXtaS0kYtEiWiDlj2YA5KSgiNG02X15DDtvf6o2TbUk6sR4fZeG/da2jZ4Y+Uq/v9m1eB+Lq/8AqFIQQ2SvlH8Ivjl9MLLNB0ZT3ERkWObmDyTDKrH/AAuB4EHySl4TKqeAPgI1JLZDTiY0rJvNS2cagZeOQiGgucGtzKs8hjS92AAk8AmtZNkocHNK7n4IUHzyhDmnOYpJduo83f6fqiR2VRhJYds5j5kngP8Ab6+yHcca37dFnq9FQY10ZudeM8AC2AdMyRBwyWdCyVq7b9R5ZOTW3cOJIdJ35Dioemnyi2B5Ky36iq59xtEkspRooHcBGldSLNUqdfgnqP2QjsieXl2b1lNj/X9aQ/YbdTttE1Gi65pAc2ZGMwQc4MEQcQRmc1UCpSqilUN6QS10QTGYIGEiQQRmJ3J7eMXgIjVpijVgO+0MgytyIzW8Yhu1bLGrjwz+iEH2zJHwifA/XGraFV2TTyKXfaqDPje0d4T+CI37rE+pKZvZ9sHO1TaIEHXcE/14Rf3Z4+KBxI8s1T36kfgl34WuI5wB4pXZYvVzGPAC36v/AK9sTFLUW6JOX0RyfOzaadzrte9gWHq6aWtmdPC8dQ8sEYksVOYIzt3x5/bnT0bQyvRMwO2IDWgjtEgk7rwOsrfZpBpuY8Q68SQcxecSJ7iB3KYCxtDCgn/APh9kP46FltLLTTFRnLcdy2e0tMFEEEEMKqI0mTAoJPCN4prlm3pbnpdDJe3N2mzSp/CEdBLjMWXpfOXqizWlxgKrnBokrv5+zqosZsub62eFgMhwA7LWhpU7PqG6M2bYdQAzHba3tir9icp9dIsruJyjhNGI+DizX7yY6uh5TqeabzkaWx4r01+pvZFyLrrxptvfVcbPGYz7c0r7tSfIvOg6X3RyvZdmS7Gmp1lrhUWHtJ6zCkR1Dt6lnfe58tj+Lis36rWPsiRjMkkyc021oaA1ogBZjV0U6i/eAYzGpmDS484jLFT2JBtnyj+DHhl9EJ/cqVwW3biN/AnSHsEadNUiLx5lYGzUSZuCeA9FGnYsrgWHjDbaWzcEssGZrWyOnrWibjExAwKnMEWPcY3p22q14cXEwUvW2bZ3sc1rACQcRhB3896WVg0qWw0tb+vCNYiaWpalJRwWlE5Hq+z+tYeHalNwnAdhvfy19keL2lsauypepNLm4AEAmYAAy1gCQYIM4RBPRs20KTmxUIa4ZgkCOcSNxGBEJzEDR0XPNMmY2UFjbCbXzv8AWIXqK1p15UkHPJnOVhEjSyBLUINB7TxMdnYljrWCk99XqvfADdQ0SZI0nQHGMTnATtD6VuqtA6zGTjoXHCBlMCZOUxuKZfcWXxLHvjK7Fkj4N/E/VEhBHY97r/Weaj9n2X/jbyCaps6SPgW7yT9MLpKVfVCjusI3gjJz3O+Ik96YZSYz4GgcAB5LMYzjP9f11RiKK61moGBVhcHhDBKGbL+8zbD8Vhp3E/ZCldtank/fZ0tOxmF/BdTHN7S5RqSXlLDzT3YF82z/AHTFw8xcIBG4gETvgzj25rGrQZUIccCMiCQR3jGF0Rk1DEF59rEHogXy7rR1VLNxKDx498UHtblKqZlxKwyh+aLt+u31AQ45MN7pkqvCT5hZKm0tixJtM/Btcm+ZJX5w6onozdwaGgaAAeQx4lVphlM4EknUknzJjuV9QQQRmmE02nXJIlTJ8w2SWjO3coJNu3KPLm0totUM02YPfGdnvfIByzFbWucyBcnIKBF08tu1eaoVpwelPmBT/wAuX02/e5sfOiioZotwlLVnYwnFBTrMmBGmpKBxXmPfCLKWF8IJzIA7yIbiCCN1gt1msOMSNDvDUyfvc51HUrsB+re3siLgipY05hWD3DIrsqTlHrU9Zw/y0U+1cJh/T8pT3vMkITiB6LMnfkQ30xX0EUNFquKzlb0jlPpz60mYPksjfSViQk8oVC2pmL3pf+EmKRgivQDerdOdyvmXvpQH8PbvSYPpWHK71UJ/xUvxNvpEefsVovbc/YMpKKQs2QjOUDsXRWN5nTIJI4YreEc3aVqbYWNcRMmIy0mfId6YoHpSQME+90dEf8VJ8Zi/WYj5tdQlr+kU9rhrc6gzyubaHIRLNsCkOtLT/sk+yNfc7RfFKf8AZJ9kcge0bRkwj+5busgdnB4hJLvDRKLCpkAdQdPoBjRt6aIf4qV4Nf6Icjd+jGlLT/sk+yMVewad5byhJlLjRkuqKCMSkXBAyIvEfvCzVh5hW92I1TCZvnQD/EA9yTD9Cw2m7/0K6O7dyEfxWikpispKtfEpKsOog2I84d7Gpld7NY6DAcsV8jYg3FhncX4ZWuR6k0I1SArzorck8oFM4ZlSYAupbDnkSbBS17DM9lzwiL2pymBBZJS4rC4LFwO0lQAR1WPHO2kV7VbSAGGWFBth5xMQFg2JSovke+9rtY2ItFExIoDVVNc6LtqvlKrG9UonyEH+stEFXb0Vc3158wjqxtb9UWHsiGhxJErBMxl+c6HNBQuA9L3znCTcdHS3HWLikwaKhqvOqTacx4+WX0QnBBFwAMlQknNEbpNIBGfWLG1m4NcZ5cI0giVC9Obl7a9NopNSfWZcMzsmJ0Xy4DECR2ERPRUHIRtXOooyfxZ6DylzP+15mLfhF7briE8x15oKonlv2hzlckkHKTKHg8wlm/dEuK7joN/6rndpVb/8Vk/ZASv9Ec/DjBDQEm8y4ogjIEL1kqWuDA7MSgMwMmHBMJN0GZxAC2eWsWVUiUyB6/8AbPq0PlGsZxG1r5a27euMQIWQD1aa9nDPxI84xC9PWTEV0RyqzFCzANGUEMAfEQhAhEOKGoWW2JpSTRhZcL4sN2UgN0SDcE3HdDeCBCfbF2aZ8+TJ4TJioc88NxjPZZb+UejQOqKb5I6IvWNM+DKllvnv0F/dMzyi5Y8R7S179pbTHyjxdj5QurYGQwnefJEEEEedTyIIIIEKh+ULZpk106wsrtzq/PAZsvllohNn0U2c/NSVLOQxwggXVVLNmSB6oMWJyyURHMVA44pL9tvfJY/zIrIiPpGyq/TWOm7WIP8Abh4xPeuHaGXKpHfzQDBBBHRWCIIIIELbAcOPhfDqL3sCctbZjPtEaxlHIDAG2IWbtAN7eYEYiFKIXmtK5uWFVhMBfnGLAqwJHN4FtdbC973veEIIlQup5MdocxtOnN7B2Mlu3nAVUfr4I9Ix5Loagy5suaNUdHFutGDC3lHq30pPxhC1cYgpmgcCF5Y2vNx1E5/xps1v1pjH64b08hnYIguxvYddgSbX7AYWp6J5hLaKCMTsQAoZiMRvqLg6RKTJYpgLqTfUkMrF1JIswawAuAbE2KA53BhiYwS8Tmm9DIEsM7OgI5sBjiZRzilmWy+u2HhmNQdQYZbQqRMcuECXJNgScznncn2WhGdNLEs1rnWwCjvsoAjSCEToiCCCJUIjKgnIZnqECgk2AuToBmT3DjE2ZUukUFwsyoN7Le4l5WuwBzPHOxuCLcYgmFIEqMq6F5QUuLYgbdYItdTfiLi/UctQQG0KT57OxZzcm2fYBYDutCRMSASoMK4eSCgwUsyeRnNmGx/MljCP3jMjvIjd29n+j0siRxSWob5ZF3P6xaJKPmNur9PaKlUZEmOGQ8IXfosuMDUQQRX1VvrLl7QYGq//ADpeW62GEPiC9Ho4mIOIlgcIA8Yiy2SpaS4UxJAJyJ7sAcTpvUvqNZF7VWDBGBGYVV1zPKPQc9s+dbWWBOH6M3b9zGPGKJj0zNlBlKMLqwKkdYIsR5R5sraQyZjyW1lu0s9pRit/ZHsfZivNOpROhvDgRB5QFy7e2HB36wSMEEKBQUY4rMDoSBdTYDCNWa5J6gFPXHpyUjCThedTYUlzOcltjx9BWu8vA2H31bdHFqMzcQhBEqEQQQQIRBC9JRzJptLQt3aaEgE6AkA267GJOXSiQgmkqxbCQbEjCykEIVYXbMkG49RhlliglSAm8jZpV7TbCwmFlueiUUEF8GdrspsDeLA92B/Lp5zf5Yrauquca4ACjJRYXw8Azatawte9uEJ4zFXMvZqzX3clO7XnCnZ5CkMUmTMIz6N7jE3AnO1swwtcAixhZlbMZOaaYxQMZgS/RDkBSwXQGwAiS31pea2hVy/+PMYdztzg9jCIWJaMFBzRBBBFlVEEL0ySiswvMKsFBlqFxB2xAFWN+gMNzfPTzQgQn2ztqTJIYJbpWsSM1YZYh4E69ffdkzEm5NydSYxBEQplLyarCkyXgQ85gGJlBdMDYvem+BfQ9YyiQ3RoOfraeVwMxWb5Ke+MPEKR4xERYPI7QYqidUEZS0CD5Uw38wEP60J7Rr9BZalQbjHE4DxK0osv1Gt7VbcEEEfM13lG7RWdMPMyyZalbvOHrAEkYJPDHkbsfVuMiTlT+09nyk2wKdEUShUUyYLdHCRKDKQdb3N763PXF5RUu3qcfdVnyBFdswA8bPKZm8CUXyju7GtFw1RkOjOW8uaAeMnDdprKlppzd4+EFWJQUs2nZZS++U+i3PvkmwNlJP3yXwB9Zcr4gbrLQQRxHPL8Tn5/n55nGSmg2MAiKR5UaDmq92Gk1UmjvtgYeaX+dF2k8YrnlioscmTUAeo7SyfzZguL/OS3zo7Xs/ULLYNxF09+I75ASttbNLhiqqI/rvzHsghesrJk1g81y7BVQFtQqCyr3AQhHvVx0QQQQIRC9FTc41r4Rxa1wvAX7L2GWeehgYyuaWwmc9jbESV5vm7DDhHrY73vfK1oKSreU2JDbsuQD32IvnwiFKmZdQKaVMlCa1ntjl5qXeW3RuFJAFmuDfIhWBNysQtTUNMbE3bkAABc30AGfWdTxhOY5YlmNydSY1gAQTKwTHY+5ZuqORlyS7CWurEKO9jYe0x6m+4krqjKq67ELWk0OlUlyz0HN7SMy2U6XLmX/OUGUR5IvnHCRdXLpsvHTyaoDOU5RvkTQMz89UHzopWLUjLQqVBDiiCCCNFRKGbdAhGYN1N7AA3LXFsyTbMnRQITggiAFJKIIIIlQiLq5KqDmqATCM5zvM8AcC+Flv8AOioNn0mM56ZDK+IlzgTCAMzi67DhcXj0Rs6jWTKlyF9WWioO5QB9Ueb9papFnbTGrseAHqQnrA2Xl25OLRmCCPFyuqiOH2ruvUvWPUpgwmpoZwBcgladHWZfo5HpCwjuII2oWh9EuLdRB4SD5gKj2B0Tp/pEaswGsbRgiMm3Z62XZmrlJjpZnSInfTZ/P0NRKAucBdR+dL6agd5UDxibjEbe8vD2vbhdMgaCMeZjE6qpYCCDqvMgMEP9vUHo9TOkWsEmMo+Te6fulYYR9Qa4PaHNyOI7156IwRBDijkYm6WSqMTG9uiCAbZHPMcIU2lSrLICuGGFTowbMC5IIsM75XJGkEqYTZXyII7Rpr2nq7I0ggiVCIIIIELoOT+g5/aNLLtkJomHulAzc+zogeMemopXkK2Xinz6sjKWglKeGKYcTW7Qqj9eLqhSsZdCboiGqM3k2StXSzqZvwiFQeptUbwYKfCPLk6QyEq4wsCylTa4ZSVYEcLEEeEetQeqKH5Y9hGnqvSUFpVT0jbQTlFmB7xZu04+qLUHQYVazcJXM7B3WqqxWeQqsqthOJwudgdD2EREVMhpbvLb1kZkbj0lJU58cwYn92N8Z9Ajy5UuWwdsZLhr3whcsLDLKIKsqDMmPNIALu7kDQF2LEDsuYrRNq6eoKgHR/KRmeOP2CydcuiJnVTm7G58+vR3lPLUIwU4ywNyL5YVMQldTGVMmSWIJlu8skaEoxUkX4XESmwd6KmjVkkMqhyGbEgbMC3HsiMnNMnTHmWLO7M7YV+E7XOQ0uTBSFp6d5qEdH8u/TMxl3nuUOuXRdz1TeJPZdHmrvYXJCK6khjZScYt6uFidRexzFrw6oqVZaNzqjFkDjvYK1jlkRi6LWNsmQqdRDLae0zN6PDibWxm97lbkLniNrnNj3BmZyURGa6PckmdtGWieoLOxsBhWTiKqpXIpiKC5AuLZCLpiruRmgzqKkjTDJU9/Tf/ALcS3KztKdIlSDJmvLLO4JRitwFBsbax43azTbdpCzsIECAdJguPp3Lp2c9FQLyu7gjzz7qq3T02f+1f7YPdPXfHJ/7aZ9sSfZe0D528neij9oM+k+HqvQ0YvHnr3T13xyf+2mfbGfdRXfHJ/wC0b7coP3Yr/W3/ALeiPf2fSfD1XoWCOS5Mq2bOosc2Y0xucmDE7FjYWsLnhHWx5+00DQqupEyWkjDsTtN99odvRBBGIxVlTnK5Qc3WLOAynSwT2vLOBv3ebjldn0lyJj9FQRa6k4mNyosFN1JUjTPTuuHlHo0anWeyqTJbLH6tpg5sh8j0SSmfA2OgMVHtDaWJRLQnCBhxEAFlyFioy0CZ8cCmwj6HsWqaliYDmJbyOHhC4tqYG1Se/wDXelK2uwORLt0QyWAGFLsGPNkHpAnFqOJBvrETMcklibk3JJ1J4kxiCOsMEscV3+2txJEjZ5rVmzS4lynwsUw3coCMlvbpHjHAQ5evnMuBp0wrkMJdythoMJNrCw8obQpY6Nakwis++ZJBiIGGH63rSo5riC0QiM4ThZ7dFbAm41a9hYm50OnVCsymmKMTS3A6yrAeZEdDyc7BasrUl/g1BecbA+96YMwbFyQvXYt2wyTAlUAkwro5M9ieibPlIwtMmXnTOvFMsQD2hAi/Njq4wIzCRMmU8BAhYAiD3x2AtdSTKdrBj0pbH4ExfVbu4HsJidgiAYxUkTgvJVVTvLdpUxSroxVlOoZTYjzhKLi5Y9z8YO0ZC9JRaoUDVALCb3qMj+bY/BinYeY68JSL23TCn93d0qiuV3kGWAjBTjYqbkXysphvIZadp0qYLzEaZLax6L2uhl+r6uIEnTKx1AEOd3d6Z9FKYU5l9JgXEwAknRebUEGwAuSevzhayoabMea1sTsztbIYnYsbDgLmFafvJr1Okjo/lj4u/sV3XA1t3PXcifUu9sTEgaAnSwt52tnqYRghakpzNmJKBsXZUB6sTBbnzhuQBJy1WWJV38nFBzOz5N9Zl5x/SG6/uYI5/ln+9U3/ADH/AIBHfSZ0pFVFdAqgKBiXIAWA16o4Dljmq0mRhYH3x9CD8AdUeB2XWdV2myqfmc48w4rsWhobZy0aAJo2+FH9yvQ8Tc96MJVuba2PBh9a1rX4xW0brLBRmDZqcwbAYcgLcWYknIaBSY0j2djsVKy3ujnrGTO/kFzKtVz4vaBEEEEOLJXRySf3D9K/+mO0ji+ST+4fpX/0x2kfNdqf1tb8R813bP8Aym8AiAGMExmE2uLCHBbESo/eGh9Ipp0j8eWyjsa3QPgbeUecxHpyPPm+VBzFdUSrZYy6/Jme+C3YMVvCPU+zNoJdUpOOgcO4wfMclzbezBrhwUNBBATHrVzkRhxcER307k4w0pqvSr2k8/g5vql48OLnPC9vCOChazWyjaJNF0xngR5gK9Sk5nxBWFvbv3T1dE1LLlzg55rN1TD0HVj6rk8OqLG5MN2PQaQGYtp860yb1rl0JXzQT4s0V9yQboekTRXzl95lN72CPvk1fhdqof3rfikRecK0bLSslPoaOUkmd5/0mg51Q33ogggi6uiCCCBC1ZQRY5iKD5TtxzRTPSZC/wD5XOg/AufgH8wn1T83qvf0IVVOk1GlzFDowKsrC4IORBHERdjy0yqPYHCCvJkEdxyhbgTKBjPkgvSk66tJJ+DM616n8DnYtw8ONcCJCTIIMFEESFFsSqnLzkqnmzEuRiRCRcai4hChpuca1yF1ZgL2ByBtq2dshmeEVFRhJAIJGcHLju70QV02wdwHqqVatZyKG5yyFCT0GZbXvxw+2IqopzLogrCx9JJIt1yEPj/vEnS7yT6SUaZZriWFXCgEvpYndpjK+Fuix7dGyzF4jqupeZRhnJJ9Jbw94TSEgLTfms4Fpf1Y0HWwOAkxGOOOGi3/AIcdUYxioO0EEEdBLoY5RYG9G4kiloWq0mTmcCV0WKYem6KdEB+EeMV/DibXTWXA02Yy5dFnYjLTIm0KWijWe+m6m+6GmXD6hhh581oxzQDInd2K4OST+4fpX/0x2kcXySf3D9K/+mO0jwG1P62t+I+a7Nn/AJTeARHPbZ3yo6WaZE52DgAkBGYWYXGYEdDFO7/SFmbYSW3quaVG4dFmCnPhkTGmyrLStNZzKswGl2GeEdh3qLTUcxoLd4GK7T+0nZ35R/2Uz7Ir3lF2vTVc9J9Oxb3vA91Zc1YlT0hncMR80RJco26tLRypT06sC0wqcTlssBOh7RHAx6fZFhsfVtVnLtR1iOBwA5Y5hc+01qv8upGmSIDFkUG59G+y/TGVud9Hmzb42tjVXI6OlshlFbx1LLbaVpc9tOZYYM78csexL1KTmATqulffmtMk05ZObMvmrc2L4MOC1+u3GNtw90Jm0Z+HNZCEGbM7NebQ8XPsGZ4A43I3MnbRmWW6SFPvk62Q/Ml39Z/YNTwB9C7G2VKpZK08hAiKMhxJ4sx4sTmTAKdKzyKTQ2c4Ec1dodUxcZS9FSJJlrKlKERAFVRoAMgIcQQRkmUQQQQIREbLpnEzFbLETqNM4koIEIggggQk5qBgVYAggggi4IORBHERUG/XJWVxVGzxcatTcR1mSTqPzD4cFi440dos1xacFVzQ4Yqj9xt6xS0xkTJJGB3uWbCcTMBhwlcrEgEk5XF7Xjk6uckhVSViVgcRUlgQSFuJq8bgLlpdbjI533vJulIrFJN5U3hOl2D6W6V8nFiRY8NCIpXebk9raK7YOelD8LKBNh1unrJ7R2wWejQY972CC8ycTicd57TkAsal+6AcQMuxco7EkscySST1k5kxKf4Ef9S3+QkRQMSv+BH/AFLf5CRvW+Jn4h5FZM14Lc7r1nMelcweZwCZjxy7YCL4sOLFpwteIeOwbfyYaL0HmFw8yJGPGb2ChcWG2vZeOPjOyPtLg73hoGOEHMdvWOPJTUDBFwzvQTE5W7s1ciQ02fTlZYwsHDSTm5VRiIYthzyAGrZxBMLx2O3t/ZlVSmkaQqA830w5J6DK2luOH2wWl1oD6YotBbPWk4gYZYjHPQop3IN446cU/wB198Rs+ilKZBm85Mnm4fBbCZY/FN9Ykf7Wl+Jn9sP/ABxwld/dKX5VV/FKiJbQwmNkWOu51SoyXFzpN5w+dw0PYtfeajAA06DduCtH+1pfiZ/bD/xxyO/df6RULUYcPOSJD4b3tiS9r2F/KOm34qdlNSEUgpuexS7c1LVXti6WYUG1o5PaskM8jFkq0tMzm4FlwgZX43IAHbC2y6NAPbWp0jTJvAhxdMC6de3yWldzyC1zgcsu9QSqBoAIVVlwspXPVSAL3yABY6KMzYakw42lSLLIAdWyU5Yr5qCSbgWF79fUcwYlN29za2uIMmURL/LTLrL8Da7/ADQfCO+4iJJSYBmAudKjWw74sbcfkxm1OGfWBpMnIhNJswfTLXtOZ4WyMd9uhycUtERNf3+eMxMcdFD/AMNNF7zc9o0juIwfXJwC3ZRjFybUNHLky1lSkVEUWVVFgBDmCCF0wiNSI2ggQiCCCBCII80e73afx2b5J/LEs21tviWZpmVYAYKVMk4xiXEHKc3cIdMWl8otdUSvQMEUBs3ae8E9S8qbUkBsNzLAzAmE26GYBlOpPBrLqbQ090m3sunWZqZg94OaLbE497zUXFzp0h1iC6iV6JvwgtHn19r7wBQ5aszdpeHmTjxKizDdObuBha4PGzdRhOj3g25NQzUn1GDCzq5l9FwpClZTc3Z3uclGtj1QXUSvQ8EebJ++21kOF6ucjcVZVUjvBS4hP3ebU+OzfJP5YLqLyu/eDcKgrLtMkhJh/Cyug9+trdFz8oGOH21yUT1kc1Szkm2mmaBN97axlhMN1uGNxe/RGccR7vNqfHZvkn8sHu82p8dm+SfyxMHDHIyqkNOia7R3Tr6ckTaSaLG10HODzlkxDPkSpyI1ByI7wY6A777Svf0yZfr6H8saVG+e0JgtMqmcdTpKYe1I2FU6hYmiNCoKCHE+td82weEqUv8ACohC8W6UKvQnepSu/ulL8qq/ilRE4hpeHabQmBVW6lVxFQ0uWwBa2K2JTrYeUSFLvdXSspdQZfyJcpf4UjNjrgjtcebi77qxpTru8BCX3e3Tq6hsSU8wqMxiQhWIzCksLYes+WcWH/ZxUTWlsZkunVZEuTYAzWQqQWAB6LDVbk6HMGK993m0/js39z+WM+7zanx2b5J/LFXkucHbvurtY0BXJsTk3oKc840s1E2+IzJ5D9Im9wlgl78bX7Y7EC2QjzV7vNqfHZvkn8sHu82p8dm+SfyxUgnElaCBkF6XgjzR7vNqfHZvkn8sHu82p8dm+SfyxF1F5elrxmPNcrfjarMFWsmkkgAe95kmwHqw6beTbYFzUzbZZ3lcSFHDiSILqmV6Kgjzl7q9s3I9Km3GR+9dQPV1EecZO9W2fjU3zld/VBdRK9GQR5qmb8bVU2NbNv8Aoz26hY193m1Pjs3yT+WC6ovLnAf6/wDcdXP35cusxKeUjKZjXuSXeZJmSS81ssbWmanM4VBPGMwRdQkzvkcQtTS8IIYIXmGxLVRmHFcE4hVz17OiRpmVW+kx0SXzKqF5m5RmUs0l6dle4zDEU8tb5ntyABBBCJTev3pM249HSWpcswlsyF0eXLlzpblbA84JcslgAQR2w7p992lzOel0slJgQygVMwIJWNnSWJV8IChit9baFc75gghErlXw3OBcK3OFbk4RfJbnM2GVzGsEECEQQQQIRBBBAhEEEECEQQQQIRBBBAhEEEECEQQQQIRG8sywOkhJzzDAezCe2MwQIW/OSvyR/X/+YC8r8mf1/wD5jEECEm5F8hYcBe/tjWCCBC//2Q==";
    private static final String FAILURE_IMG = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTcy7WoHI7Z1TMlAfPo0AY2BxwfaPFDL8Rk3RTIH_XRXyuoMJivUhfxFDWLG3Jyx3FA6j8&usqp=CAU";
    private static final Logger LOGGER = LogManager.getLogger(GameProcessor.class);
    private static Game game;
    private int questionId = 0;
    private int answerId = 0;
    @Getter
    private int numberOfGames = 0;
    private String username = "User";

    public GameProcessor() {
    }

    public void createGame(String path) {
        ObjectMapper objectMapper = new ObjectMapper();
        InputStream is = GameProcessor.class.getResourceAsStream(path);
        try {
            game = objectMapper.readValue(is, Game.class);
            LOGGER.info("Game object created");
        } catch (IOException e) {
            LOGGER.error("Fail to parse json game object");
            throw new IllegalArgumentException(e);
        }
    }

    public String generateQuestionText() {
        return game.getQuestions().get(questionId).getText();
    }

    public String generateFirstAnswerText() {
        return game.getQuestions().get(questionId).getAnswers().get(POSITIVE_ANSWER_INDEX).getText();
    }

    public String generateSecondAnswerText() {
        return game.getQuestions().get(questionId).getAnswers().get(NEGATIVE_ANSWER_INDEX).getText();
    }

    public String generateAnswerMessage(String answerIndex) {
        if (!checkAnswerId(answerIndex)) {
            return "";
        }
        return game.getQuestions().get(questionId).getAnswers().get(answerId).getMessage();

    }

    public boolean generateNextQuestionId(String answerIndex) {
        if (checkAnswerId(answerIndex)) {
            if (game.getQuestions().get(questionId).getAnswers().get(answerId).getNextQuestionId() == FAiL_ANSWER_INDEX) {
                return false;
            } else {
                questionId = game.getQuestions().get(questionId).getAnswers().get(answerId).getNextQuestionId();
                return true;
            }
        } else {
            questionId = 0;
            return true;
        }
    }

    public String generateEndGameMessage() {
        ++numberOfGames;
        if (questionId == MAX_NUMBER_OF_QUESTIONS && answerId != NEGATIVE_ANSWER_INDEX) {
            LOGGER.info("Quest completed with victory");
            return VICTORY_MESSAGE;
        } else {
            LOGGER.info("Quest completed with failure");
            return FAILURE_MESSAGE;
        }
    }

    public String generateResultImgPath() {
        if (questionId == MAX_NUMBER_OF_QUESTIONS && answerId != NEGATIVE_ANSWER_INDEX) {
            return VICTORY_IMG;
        } else {
            return FAILURE_IMG;
        }
    }

    private boolean checkAnswerId(String answerIndex) {
        if (answerIndex != null) {
            answerId = Integer.parseInt(answerIndex);
            return true;
        }
        return false;
    }

    public String generateUsername(String providedUsername) {
        if (providedUsername != null) {
            username = providedUsername;
            LOGGER.info("Username is " + username);
        }
        return username;
    }

    public String generateProgress() {
        return switch (questionId) {
            case 1 -> "33";
            case 2 -> "66";
            default -> "0";
        };
    }
}
