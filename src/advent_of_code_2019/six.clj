(ns advent-of-code-2019.six
  (:require [clojure.string :as string]
            [clojure.set :as set]))

(defn add-child-edge
  [children-map parent child]
  (assoc children-map parent (conj (get children-map parent) child)))

(defn add-parent-edge
  [parent-map parent child]
  (assoc parent-map child parent))

(defn build-orbit-tree
  "Builds a tree represented by a parent/child adjacency list"
  [orbit-list parent-or-child]
  (loop [[this-orbit & remaining-orbits] (string/split-lines orbit-list)
         orbit-map {}]
    (if (nil? this-orbit)
      orbit-map
      (let [[center-of-mass orbiter] (string/split this-orbit #"\)")]
        (recur remaining-orbits (case parent-or-child
                                  :child (add-child-edge orbit-map center-of-mass orbiter)
                                  :parent (add-parent-edge orbit-map center-of-mass orbiter)))))))

(defn calculate-checksum
  "Calculates the sum of each node's direct and indirect orbits"
  [children-map]
  (let [branch-fn   #(contains? children-map %)
        children-fn #(get children-map %)
        root        "COM"
        nodes       (tree-seq branch-fn children-fn root)]
    (loop [[current-node & remaining-nodes] nodes
           orbit-counts {"COM" 0}]
      (if (empty? remaining-nodes)
        (reduce + (vals orbit-counts))
        (let [assoc-children #(assoc orbit-counts % (inc (get orbit-counts current-node)))
              new-counts     (if (contains? children-map current-node)
                               (into {} (map assoc-children (get children-map current-node)))
                               orbit-counts)]
          (recur remaining-nodes new-counts))))))

(defn get-ancestors
  [node parent-map]
  (loop [this-node node
         ancestors []]
    (let [parent (get parent-map this-node)]
      (if (nil? parent)
        ancestors
        (recur parent (conj ancestors parent))))))

(defn steps-between
  [node-one node-two parent-map]
  (let [node-one-ancestors (get-ancestors node-one parent-map)
        node-two-ancestors (get-ancestors node-two parent-map)
        common-ancestors   (set/intersection (set node-one-ancestors) (set node-two-ancestors))]
    (- (+ (count node-one-ancestors) (count node-two-ancestors)) (* 2 (count common-ancestors)))))

(def puzzle-input
  "N66)QHW\nJLG)GBP\n12Q)F84\nJMQ)QDZ\n1FF)45G\nT82)NHZ\nZ1C)BP4\nJMB)XQ1\n51X)FTR\n7TD)YTD\nS65)TZ2\nN56)F1B\n31N)Z26\nGPP)RHP\nXK5)SSH\nLDG)7C3\nLLS)96H\nZN6)SFY\nQDF)C2B\n4T8)Z15\n6MC)VTY\nJZ9)QYF\n7KK)W4Q\n8KL)FC2\nF2Z)2YC\nYTZ)866\n127)QFZ\nXWZ)BXH\nTXC)H1R\nC14)C4J\nC9K)BHP\n3V4)81F\n9H4)127\nQSK)DD6\nMNL)MGQ\nG2J)9VS\nHZV)DPP\nBZ6)W5J\nQVQ)QQH\nG16)6NM\n8ZC)5JQ\nR54)D2K\n6ZP)8B2\nQJW)Z94\nFF2)VK3\n3NR)XQ9\n2SQ)154\nKSB)6T3\nHNT)GB4\nH1R)NMR\nNCT)JNJ\nKPT)4R5\nZGW)53N\nJDY)4C6\n42W)QSK\nQWH)JXS\nZ8K)99G\n8X8)JLQ\nWM3)6ZP\n5XQ)KNQ\nTXD)7C5\nMV1)76W\n79T)RDK\nD4F)LL8\n9K5)65L\nSRF)JDB\n2JT)FCC\nJGQ)F4W\n1V3)ZC2\nP69)B72\n974)9JR\nZ4W)MVY\n3SP)9KP\nZWV)FXX\n6SN)QW4\n33J)4Z7\n6SN)974\nFJR)LTK\nFJR)B7Y\nN1G)ZFD\nZY1)1NZ\n7RL)7T8\nFXX)424\nCFD)TW7\n7H1)V6P\nKQD)KL1\nKZ4)5ZD\n73V)QPL\nKRX)KM3\nS2N)F6S\n25L)LQM\nCQL)PJW\nLSY)ZY1\nJK7)R1N\nN56)YQV\n255)66S\nW5J)VHS\n7DZ)7RG\nH8N)ZHR\nZ4J)VJ4\nX9K)CJ8\nZLR)PR9\nQBW)X37\nQ43)56Y\n78Z)W2Q\nGB4)QDF\nTYM)L8G\nPB6)ZKH\nZ6Y)4HD\n17T)H1F\nNB1)D72\nNC6)ZTH\nT9V)G1C\n6QP)1LV\nY8Y)42N\nB3G)P55\nZW9)JXZ\nTBN)6RV\nQ83)T53\n58F)FJR\nTGN)S8R\n1H9)M9P\nF6K)ZGW\nVJH)W1L\nZGF)W44\n4T3)QW9\nJSY)FF5\nQBD)BF8\nMKQ)4MM\n6MM)TYW\nH1Q)SGJ\n8RF)7RL\nZ7C)GYM\n7VM)YZK\nCTD)Z4J\nNP5)98J\nX25)D88\n7SR)LY2\n2YN)7DZ\nPD1)KL3\nQLQ)Q83\n1LV)H5C\n1QK)Y3R\nX6C)3HP\nWMC)LGM\n412)QSD\nND2)M6Y\nGX9)35L\nP35)XZP\nFC3)3HF\nQ95)22V\nCS6)CF8\nHQR)JZ9\nRJF)V6F\nFZC)3ZN\n631)1H9\n6T4)WL1\nLKG)LSY\n2RW)J8F\nBZ3)Y8Y\nMH9)1WD\n8TQ)QXT\n9KP)RP4\nZFF)QSZ\nP77)RDB\n2NH)NKW\nR3H)QK9\nW6C)CMY\n6T3)ZSZ\nHWM)56V\nYC1)ZVB\n1G4)YYW\nMTV)LXV\nXR7)1FF\n95M)T2J\nC79)8ZC\nRXT)H4Z\nQ2F)8D8\nV8S)97R\n1H9)7SR\nJR9)GY2\nV6P)NZ1\nL4L)KZ4\nPWC)MXY\n34D)YC1\nTW7)D11\nC6F)KWY\nN5N)3V4\nTZV)FDY\nJ71)MK7\nBCC)JLS\n4SM)GKL\n649)BFN\nRK4)319\nP81)HMG\n474)VHJ\n1GS)356\nSHG)92Y\nHNB)WMC\nYYP)68F\nM3L)FBG\nLNG)MT2\nQW4)6DB\n1RK)YZG\nNN5)NSQ\nX1S)ZGZ\n9BQ)R3H\n4HD)39W\nN44)SJN\nNTZ)3CQ\n3TK)985\n39J)5J4\nVBC)X25\nDJ4)7XW\nSVJ)BGV\nVVQ)7DD\n85J)8RF\nMPX)58F\nK9X)876\nZY4)B9W\nFJF)BVR\nC45)SYW\n11V)5HR\nFXW)9P9\nXKY)94X\nZ6L)K5Q\nPTT)7HP\n95M)85N\n5RX)6NR\n6NW)5GX\nRY2)88X\n382)TQ5\nQYF)M8Z\nVTY)Z63\n4ZB)63M\nLY2)969\nNTV)LCL\nSB2)CFD\nPR9)8L9\nVHJ)PJ6\n516)KMC\n51B)516\nQ43)DDK\nJLS)ZY4\nJQX)VZ9\n7D5)S2Q\nNXT)SHM\nB5B)WPG\nP77)4T3\nRDL)SBZ\nG9T)ZDN\nRLM)75B\nBVR)LHY\nZ15)9XQ\nLN5)5VS\nLQM)YLK\nXR1)D9X\nQ7K)KSB\nPP9)N5X\n2LT)6T4\n3C8)1XC\n6NR)5JH\n8QM)TGN\n36Z)4R2\n8YM)NB3\n4N6)6L7\nSGK)8Z4\n1BK)VQX\nP55)PBZ\nY5T)D4C\nGJV)3RC\nKCB)81Z\n63Q)NBM\nGYV)MNL\nC37)9H4\n23V)3WR\nMLK)B2G\nN72)YCF\n46P)CTD\nHNC)SQ1\n5GV)GV3\n7WJ)49H\n6VV)JB8\nH13)8GT\nFJQ)6YW\n7Z4)7VF\nMK3)GPY\nH4K)65D\n4W3)1LQ\n974)TLZ\nT6W)5T5\n6DD)LKG\n4C6)78H\nD7K)TYM\nTZ2)W8B\nDHF)LH8\nBK5)BHM\nZDN)9CV\n6NM)MKQ\n1QK)5S7\n18W)YOU\nRKS)C57\nMH4)TFY\nSWR)8HD\nJGQ)631\nT6B)Z3Q\nXV1)9GM\n3N7)WPL\nVFT)DYB\n223)THD\nSR5)G9S\nYZG)GYS\nNHZ)VCP\n3JG)PHJ\nFNG)NJL\nWPL)6Z3\n6M7)JMB\nKW9)KLR\nFZR)3BY\n463)YSB\nVX8)K3M\nQSZ)YRH\nZSZ)QS3\nHR7)FL3\n14W)K45\n11M)48B\nT6W)B1W\n8LC)1JJ\nLHY)WX1\nBWZ)2P8\nH6N)6M7\nPJW)XTF\nN3Z)4ZZ\nRCR)F2Z\n5V7)N3S\nCYM)NL5\nZ3Q)3D7\n319)FBR\nC3K)C93\n3BY)ZZW\nD11)8QM\nKQK)GPM\nMH5)FL4\n2W2)MV1\nQDC)MK3\nZY4)2WT\nXCN)523\nVJD)7Q9\nTYW)MKT\n323)ZCK\n6V5)HQR\nZQN)284\nZZL)N72\nVHR)YMB\nL7J)9YR\nD5P)QVQ\nF71)FZC\n6FZ)9PN\nCN5)Z1C\nK45)DSL\nQB5)T16\n7B4)C3K\nM5L)XK5\n4R2)4FL\nJMQ)QJW\n969)WYB\n88F)NK7\nZKH)76D\nYYY)VGM\nDD6)RK4\n356)6P4\nVF5)DDM\n48Z)XWR\n6RQ)H2K\nFQX)RTZ\n96H)M77\n8L9)CN5\nGL2)754\n4ZB)11V\n83S)S59\nMYZ)253\nYRS)2VM\nN23)7GC\n85N)87L\n78H)MR9\n8H1)7B1\nTNR)6P7\nBND)624\nXCW)Z7C\nRFV)4SM\nX1S)ZTW\nTTL)5Z3\nSB4)57B\nCS7)ZD1\n9FR)TDD\nLNG)T73\nKV4)KWV\nC57)4R7\nF1B)96Q\nZRR)5XH\nRDK)3CS\nKF7)4W3\nTDD)SGG\n4FT)BQV\nBWZ)WWS\nNHJ)SVJ\nS8R)TDS\nXLX)BZ5\nHK9)D73\nS3C)G9Z\n6P7)3W4\nHPQ)P9R\n7P7)LVN\nXDL)CQV\n65L)182\n1NZ)C91\nW9T)JXV\nTCB)2TV\nLVN)MH5\n1WR)VHF\nKMC)FFW\nKGW)JK9\nG8J)8XV\nNW8)C34\nQSD)GMK\n253)Y1N\nRKT)1XV\nZLS)CXN\nZ7R)ZFF\n5T5)7N8\nTT4)HPH\n523)6DD\nGYM)9K5\nFFK)XRN\nFVZ)42W\nHQG)7HH\n9CV)PCJ\nWSP)QZM\n67K)C14\nNT8)QD4\nVNZ)XT4\nQW9)LLW\n92Y)N66\nQXT)GYV\nJDB)9QQ\nPCJ)YDN\n5WM)C55\nM8Z)97X\nH7J)R2M\nNB9)2BS\nK9X)GPP\nCMY)GZP\nQR7)46Q\nFTR)JDY\n985)W3N\nT73)CP7\nGZP)VX8\nV1P)CSP\nB3W)ZGF\n14C)3KK\n9XQ)183\nZD1)6YN\nD4K)KPT\nGV3)BS8\n38S)XLX\nX3C)Q1V\nQX7)1MP\nVK6)CCV\nDDK)46P\nGYS)H74\nXQ9)HNT\nG54)5KV\nZZ5)JXQ\nX37)LLS\nL8G)D6D\nBZ3)N6T\nSS9)475\nD3R)3NC\nJWX)YZC\nWP5)CB6\nQS4)D68\nPY2)745\nXWR)9YP\n6L7)8NG\nS2Q)11M\nVNR)MBD\n5M4)6MM\nVF7)CXK\nXR7)Q76\n3CS)SRF\nY7J)VSY\nNPX)8KQ\n7MX)LML\n7F6)X3C\nYDN)LZ9\n33J)8HY\nGZZ)2PH\n7Q9)PYK\nD1K)5V7\nNBM)P7W\nXKD)QBD\n5T3)YMV\nMT2)PQY\nN6T)7Z3\n2RZ)63B\n5QK)KRX\nM4R)FQV\nRKT)LYW\nSBZ)47J\nD81)XR1\n54Q)6BL\nJKV)CTR\nML1)K9Y\n4Q2)63Y\nPWH)BZ3\nXQD)LBF\n3KZ)63Z\n3N1)3XT\nWQ2)VDZ\nQK9)V6Y\n6BL)N41\n79V)LBR\nC91)WQM\nF6S)79V\n4JQ)DH2\n35L)RKS\nNN2)6R1\nRNP)VV6\n53N)9XG\nV6C)865\n5ZD)23V\nF85)YHM\nF4W)JWX\nWTS)N7Y\n8Z4)WP5\n78L)21N\nXGZ)G8J\nDB2)322\nBS8)JKV\n3DX)BY1\nD2R)YBN\nW8B)J48\nLLF)R7V\nB7T)XZQ\nQSD)B3G\n81F)K9T\n3HS)GHS\nRN2)NST\nPJ6)ZBX\nTRY)D4M\n3Y6)MD7\nB6R)NC6\nLXS)FXJ\n191)RKT\nN5X)CGW\n761)46M\n3NC)5Y4\nBYN)XV7\n3P1)HJB\nW3N)XGZ\nLBW)FVS\n4W8)DWX\n8PX)M2B\nPB6)LVZ\n73K)6SN\nZYT)2DZ\nSVM)W6C\nPLV)K8N\n8B2)Q43\nPDS)3PM\nRD8)RY2\n7WN)XDL\nCZD)51F\nKND)P3M\n3ZN)V6C\nLCL)SGK\nY1W)3XQ\n3N4)YYP\n8X3)BYQ\n7ZF)F85\nWL1)P5D\nMR9)VH4\nJ8N)31M\nGHS)3SP\n12H)DD4\nWLN)NPX\n8Z9)M25\n6HZ)T4B\n7MZ)26W\n7QW)M5L\nC4J)RPS\nKNQ)T3Z\n9FV)NPH\nLGF)KZ2\nP7D)7S8\n866)F9X\nCTB)SB2\nSL9)C1C\n17J)5WM\n13Q)PF5\nHPV)1QK\nBF8)PY6\nFBG)TXD\nR2Z)PWQ\nTN5)6QP\nWPG)TH6\nX3C)6VV\nFRV)8VK\nV6B)2SQ\nZ79)ZN4\nV3S)WLR\nLL8)7G5\nST6)SL2\n56V)25L\n4ZZ)WM8\n2XJ)ZLR\nC93)D81\nX4Q)KGW\nBFG)ML1\n88X)LQY\n5W7)JGG\nCP7)59H\n7S8)9VC\nNZH)Y3D\nZXJ)VYD\nR1N)BZ6\nQ2N)12H\nNKW)NTV\n2DZ)8KF\nFK6)B6R\nLSY)LFG\nNPH)2JT\n1MP)FQX\n5GX)N3Z\nLL8)F71\n29B)ZQN\nKLR)K7L\n1WD)3TK\nPWQ)PJ5\nPX5)MPX\nHWD)G9T\nQTQ)Y5T\n1HC)X9K\nP9H)WK8\nVQK)V1P\n66R)RJF\n1L6)RQ1\n68M)15L\nCGW)HWM\n7QP)BND\nYMB)1GS\nHCR)6K5\nNH6)VWM\nXSW)3DX\nSGJ)W6T\n3W6)34D\n3SH)BK5\nBW1)1JQ\nCF8)PWC\n3D7)S6N\nBF6)4SC\n8VK)8Q2\nKZ2)HWD\nLYW)P35\nZH9)RXT\nWD2)JMQ\n8B4)4SH\nSJ8)Y4X\nYZC)NN2\nJV2)M4R\nPSQ)3ZF\n8PD)7KK\n98J)XSW\n1G5)FYT\nL39)X4Q\n5VS)889\nP7W)N84\nVN3)38S\nBGY)R5C\n284)Y6Z\n6Z3)MH9\n7C3)DL8\n7KM)G1W\n3N4)4DF\nLML)HQG\nLFG)8LC\n4ZZ)ZH9\n8XV)1SC\nYLK)3C8\nBMY)XZF\n9JV)ZZL\n7GC)ND2\nLG7)N23\n15D)HZV\nM2F)31N\nNN2)7B4\n42N)VYN\nPQY)BXS\nZ7F)BH1\nQS3)RVQ\nQD8)N1G\nDL8)9B7\n9Y4)T6Y\n3CQ)VZ8\nB9N)C37\nBJ9)C4H\nJHJ)5DG\nC2B)MLW\nJNJ)8KZ\nNH5)TD8\nBND)191\nD88)3BV\nW2Q)8X3\nQ89)7WJ\n624)2XN\n5Y4)CTQ\n3DX)48X\nPJ5)9FV\nC45)63Q\nGMK)91X\n3KK)QX5\n9GM)8PD\nD68)Q95\nBY1)29B\n63Y)SKH\nLTK)MLK\nZKH)WLN\n485)7QP\nCCH)223\nBHP)CYM\nW5S)HG2\n876)Q9P\nKZ1)7KC\nRZ2)BF6\nMRY)1WR\nCMC)C6F\nXJJ)PHX\nFVS)53K\nY7H)RFV\nLLW)J3B\n7HP)JNN\nXG1)NG9\n9W2)8SB\nCWB)2RZ\n99G)5QK\n13L)LPT\nL5Y)L7J\nG4G)S61\n6Y8)GGC\n3QG)52W\nT4B)H4K\nTGD)3QG\nBQX)C27\n2P5)PSP\nV6Y)Q89\nNM8)G4G\nZTH)SL9\nJJB)1FX\n48X)48Z\nTJW)8F6\nQQH)NH5\n59H)TNR\n72F)GV2\nS6N)WQW\nSKD)P58\n6YN)Z7R\n3PM)C79\n6MR)ZLS\nSJ1)MH4\nW95)LNG\n8HY)HCR\n2P5)SPS\n3GB)7P7\nL4B)5HM\n5XH)39J\nQCB)474\nBQX)382\nVJ4)ZS1\n65T)VN3\nGYS)H56\n7QB)X7N\n322)7BM\n3HP)9WZ\n5LB)JHJ\n49G)NCT\nSYW)CQL\nR7V)C9K\nCMJ)RWD\nXTF)W9T\nKZ1)Y2Y\nK1H)RN2\nF6G)KB6\nNF9)N9Z\n53K)KKZ\n6RV)P81\nCJ8)N5N\n2VM)MTX\n5S7)VJ5\n81Z)T87\nCTD)P9H\nVJ5)F6G\nGX9)Y7J\n3WR)KRP\nSH7)48W\nHG2)347\nHJB)65T\n29M)ZK8\n1BB)5ZY\nWXF)3HS\n8RH)13Q\nKY8)VJD\nP35)761\nKM3)SKM\n2DZ)YTZ\n16Z)Y4L\nXKY)F6K\n8KZ)NHJ\n8HD)FZR\nCB6)JTD\nLZW)17T\nLN1)RYF\nRSS)XR7\nJGG)36Z\nMTX)PLV\nWK7)RZF\nNL5)ZWF\n68F)SVM\nYTF)STL\nJ8F)6TD\nMYW)73K\n51F)JRV\nNBN)8PX\nGTQ)BJ9\nRDM)H7J\n4FL)QB5\n5J4)5T3\n75B)RCR\nYHM)QT4\nGPY)P32\nKL1)12Q\nVHS)RKD\nM77)X1V\nJGW)BFF\nTJ5)K93\nTT6)L4B\nY6Z)DNJ\n88V)FYZ\n2XN)WK7\nG36)TCB\n3G9)YD9\n9JB)P77\nPD1)VF5\nB72)16Z\nQNR)SV4\nSGG)W5S\nWWS)FCN\nY4X)3N1\n9JR)8Z9\nFDY)HB2\nJ3B)TT6\nWSS)LVH\nYD3)VNR\nHRK)VF7\nH5C)18W\nK8N)Z6L\nVZ8)33J\n26W)64S\nZ93)PD1\n6RS)615\nJX3)VQJ\nD6D)BVY\nLQY)YYY\nB89)RLM\n7N8)V3S\n8Q2)GJV\nSKH)XJB\nRP4)PSQ\nGBP)B5V\nP9R)4Q8\nG9S)HZC\n5JQ)GLZ\nVHF)TD4\nJXV)VRL\n745)49G\nRYF)D3X\n889)PWH\nSH7)TTL\n4ZN)X6C\nBX1)6FH\nRP6)1L6\n66R)7QB\n5JW)ZQK\n31M)ZWV\nSY9)G36\nSSH)7VM\n7GS)M29\n96V)2C3\n424)J8T\nB1W)2YN\nBRM)KYF\n5JH)146\n62Z)RT8\nWTK)FYM\nYQ6)P79\nQ4K)9W2\nZFF)9BQ\nZ94)4JQ\n37C)2W2\nR5C)PB6\nH4Z)M4Z\nB7L)LN1\nH1R)JXC\n18J)649\n8F6)KCB\nTHD)B5B\nHK9)G45\nD8V)KQK\nWQM)CS7\nCXN)1HW\nVV6)2LT\n2XN)W95\nWLR)K91\n6KN)V6B\n3XT)L4L\nXXW)K9X\n1S1)NXT\nZC2)6V5\nP3M)Q9R\nWM3)G2J\n19M)N96\nNN2)5XQ\nH1F)J71\nVYN)SS9\nXSW)XXW\nY82)4ZN\nZZW)95M\nRMB)3GB\n475)WTK\nJXC)FFK\nNJL)YVB\nG45)8TQ\nPSP)SJ8\nX9K)5RX\n4X6)KZ1\nQZM)2WF\nGFK)SKD\nCSP)66R\nZTW)9JV\nB7Y)4W8\n7KC)TS7\nT53)LGF\n7GS)M67\n146)J8M\nT3Z)YHV\nPM9)5GV\nY1N)B7T\nRLJ)3N4\n91X)LXT\nRKD)NCM\n1V2)TK8\nRPS)VXJ\n5Z3)XVS\nM7B)JFN\nDR8)SAN\nYMV)VJH\nWX1)13L\n52W)R3G\n64S)WXF\nVK3)QD8\nYTD)XKD\nBFF)XG1\n21N)4ZJ\nSHY)JJG\n6TD)LN5\nY82)G54\nJ8T)7FL\nVVR)5RG\nJ8M)Q2F\nXMK)8YX\nVYD)RNP\nZHR)DF5\nK93)3SH\nWYT)L92\nC14)86C\nMVY)FJF\nMKT)V58\nG4M)1V3\n8Q7)RZ2\n8D8)YGG\nC1C)7H1\nLZ9)N56\nSPS)MYZ\nC4H)7JC\nXZP)6Y8\nQ9R)HK2\n97R)8VZ\nMD7)7F6\nLYS)MMK\n7DD)HGK\nD2K)T6W\nRDB)5DD\n9XG)HR7\n6VV)ZYF\nHHK)62Z\nTBN)6RQ\nMHC)XJJ\nN84)5VR\nXQ1)J8N\nXT4)XMK\nGGC)MVX\nDF5)2XJ\n57B)HB3\nJ53)9JB\nFJF)NM8\nXZF)B9N\nGY2)HD1\nZK8)J5V\nL5Q)B4V\nYSB)2SM\nQD4)ZCX\nYRH)Q2N\nKWY)Y21\nHB2)RP6\nN3S)XCW\nVBV)K2H\n6QP)NT8\n5HJ)GX9\nBP4)MRY\n63Q)QKT\n8GT)4N6\nW9T)JR9\n9YR)BWZ\nZN4)NB9\nQPL)L39\nLLS)BMY\nBDM)FVN\nHB3)BRM\n87L)XLV\n3KZ)NB1\nMVX)LK8\nH56)79T\nY4L)HHK\nXZ9)VFT\nD3X)1RK\nN19)7QW\n51X)S65\nLTN)DJ4\nQ9J)KQD\nXVS)P7D\n5TC)NH6\nZ6C)JSY\nRZF)G16\nC34)KW9\nT2J)D7K\nTFD)RSS\nBCC)FM4\nYHV)VK6\nH74)D1K\nF9X)VNZ\nYQV)1HB\n3BV)BZD\nDSL)TJW\nD27)1G4\nK92)BDM\n49H)2NH\n5KV)MYW\nWGK)54L\n2SM)SVT\n5VR)GVD\nN41)CMJ\nVQX)K1H\nJRV)XGW\nBGV)QNR\nKYF)855\nLPT)VBV\nP58)H13\nD4M)H6N\nFVN)HNC\n5DG)PTT\n2C3)DTW\nMBD)S2N\n22V)VVR\nZ63)PP9\n48B)TN5\nPY6)P7R\n46M)NKX\n8NG)BX1\nCK7)NP5\nL92)Q9J\nQTQ)D31\nK9T)KQH\nQWH)TGD\nMK7)C2Q\nRCR)DHF\nYSF)LLF\n6RQ)1S1\nDTW)HPV\nN3Z)72Z\nHGK)CTB\nG16)7MX\nB9W)KF7\nQFZ)NW8\nJFN)15D\n5ZY)2YT\n39W)T9V\nJ3B)54Q\n2C2)B4K\nNCM)DR8\nHZH)WYT\nM29)YRS\n7BM)KND\nM4Z)SH7\nML1)G4M\n9VC)JV2\nJ53)R54\n4DF)HPQ\n7VF)WQ2\nYG6)GZZ\nH7L)D27\nQBZ)61N\n1JJ)L5Y\nJ2Q)WDL\nNWZ)9RK\nCQV)NZH\nYSF)R68\nPHX)LZW\nG1W)QX7\nRD8)8X8\nH7D)WSP\n6K5)ZZ5\nPWC)RMB\nSTL)6MC\n9P9)Q7K\nY2Y)TFD\nN7Y)H1Q\n1RB)7TD\nJXS)SDY\nP32)LTN\nD4C)Z8K\nFM4)R8H\nJXQ)818\nZY1)H7D\nC27)8YM\nTXD)GTQ\nD72)HRK\n4KJ)D8V\n615)PY1\nWDL)HRB\nSVT)HK9\nG19)88Q\nDH2)QBZ\nTFY)2P5\nLGM)QTT\nSL2)3JG\nY3R)JGW\nR2M)5J1\nDNJ)Z79\n64S)BQX\n5RG)TBN\n9B7)DB2\nTD4)96V\nHD1)88V\n3QM)N3R\n4Z7)K92\n7B1)KSF\nNMR)SJ1\nJXC)WD2\nYN1)4ZB\nPYK)SR5\nS59)J2Q\nRQ1)485\nY6Z)19M\nYTD)3P1\nZFD)XCN\nLBF)7T6\n63M)G5R\nD9X)GFK\nNPM)D4K\nFC2)4X6\nJ6B)TXC\nGLZ)YQ6\n7T6)QLQ\nXJB)7Z4\nPY1)Z4W\nKSS)J53\nTK8)RLJ\nJNN)68M\nN9Z)T82\nLBR)DM2\nYYW)FJQ\nLYS)N19\nG5R)M3L\n6FH)NTZ\nD31)VJT\nSB4)7GS\n8KF)2C2\n8Q7)H7L\nTDS)NPM\nVZ9)VQK\n9VS)7WN\nM6Y)LXS\nHMG)7D5\nJB8)GL2\nF84)XQD\nQKT)323\nPF5)MTV\nVCP)NN5\nPFL)GPX\nKL3)83S\nYVB)W6D\nW6T)37C\n8QY)1B5\nF7W)CWB\nTD8)1BB\nD73)3G9\nZYF)6MR\nYD9)QS4\nX7N)WH3\nPF2)1QP\nQ76)CS6\nPBZ)CMC\nFXJ)J6B\nP5B)QBW\nFYM)R2Z\nB4V)7MZ\nZWF)1V2\nLVZ)1BK\nVZ8)95W\n7HH)C4B\nYBN)DDV\nWXF)QBM\nZCX)7ZF\nCW3)ZYT\nT2S)H3Q\nCOM)4FT\nMMK)8QY\n8VZ)SWR\n5DG)S3Y\n146)HSZ\nHSZ)F7W\nL1C)H8N\n7RG)FK6\n1HB)ST6\nZ93)1HC\n7G5)4KJ\nJK9)63X\n5F3)5TC\nNST)KV7\nVYN)CZD\nHHD)JK7\nNKX)VBC\nCTB)BCC\n745)FXW\nCXK)SS6\n3HF)S3C\n54L)5M4\nN23)3Y6\nNB3)PY2\nSFY)FRV\nQ9P)4BQ\nSDY)PX5\nNZ1)MHC\nHRB)3TT\nCZK)8RH\n66S)73V\n6X8)B3W\nM9P)QDC\n1LQ)V6Q\nHK2)BYN\n754)5F3\n7T8)1G5\n4Q8)ZW9\nLXT)Z7G\n45G)4Q2\nS49)B89\n9PN)72F\nCMN)T5Z\nWQW)PDS\n1B5)Z6Y\n7C5)QR7\nJ82)2W8\nKB6)5JW\nW4Q)Y5B\n2YT)18J\nTS7)K7K\nR68)LYS\nT6Y)J82\n855)29M\n8SB)HYM\nFCC)XWZ\n8PD)7KM\nWL1)67K\nGV2)1RB\nFCN)SY9\nL7J)C45\nNG9)4T8\nKSF)78L\n6V5)FNG\nLH8)CW3\n97X)78Z\nBFN)WTS\nZCK)DY7\nT16)VHR\n2WF)FS5\nQT4)JJB\nK7L)85J\n9QQ)LNV\nM25)PFL\nQPL)BW1\nRHP)NF9\n1ZH)HGT\n1XV)TT4\nN9Z)SHY\nVGM)WSS\nWH3)51X\nD82)X1S\nBQV)P69\nTQ5)6HZ\nQ1V)V7B\n154)SB4\nXLV)M2F\nFC2)PF2\n3RC)D4F\nJLQ)XKY\n1XC)6RS\nGD6)WGK\n5RG)HH4\nFBR)QWH\nG4G)PM9\n182)412\nLNV)KSS\nZVB)5W7\n7DD)RD8\nB2G)6NW\nGJV)YTF\nJ5V)ZN6\nXDC)JGQ\n2TV)NBN\nXRN)JLG\n319)KY8\nGPM)BM5\nC2Q)YG6\n4FT)TZV\n6DB)3NR\nJXZ)ZXJ\n63Z)8Q7\nY21)G19\nK45)14C\n94X)BFG\nKWV)2RW\nNK7)XDC\nDM2)TRY\n7B1)HNB\n6RS)P5B\nVXJ)V8S\nXV7)L1C\n1QP)768\nH3Q)LBW\n8QY)9FR\nKW9)KVM\nDDM)9Y4\n9YP)HHD\n4SH)W79\nGVD)6X8\nBZ5)XV1\nR3G)NYC\nLK8)JNB\nTLZ)M7B\nR8H)255\n8KQ)3QM\nP5D)QCB\nWP5)T6B\n7JC)Y82\n2WT)S49\nQDZ)YN1\n183)88F\nVDZ)T2S\nM67)Y7H\n382)D3R\nSKM)NMZ\n4SC)1ZH\nYGG)NWZ\n5S7)3KZ\nP7R)RDL\nS61)JQX\n98D)KV4\nRT8)14W\nZBX)8RX\nFF5)SHG\n96Q)QTQ\n7FL)VVQ\nB4K)Z6C\n4ZJ)TJ5\n6P4)FVZ\n95W)CMN\n2YC)51B\nCTQ)8B4\nK9Y)VH6\nC55)JYQ\nNYC)HZH\nTTL)8KL\n3XT)WM3\nH2K)X16\nTD8)D2R\n9K5)DR3\nVSY)CZK\nTH6)B7L\nZ26)CCH\n56Y)CK7\nL5J)GD6\nFFW)463\nCMC)ZRR\nT5Z)8H1\nPHJ)6KN\nZS1)D82\n818)3N7\nK7K)Y1W\nV6F)LG7\nY5B)3W6\nK5Q)TY7\nDDV)YSF\nVJT)N44\n86C)L5Q\n2PH)FF2\nCTR)XZ9\nX16)Z93\n87L)98D\n5BK)17J\nW79)PXJ\nQX5)BGY\nSHM)FC3\n1SC)6FZ\n5DD)JX3\n6R1)D5P\n761)5HJ\nHH4)RDM\nD11)T54\nJTD)YD3\n4MM)LDG\nVQJ)5BK\nB5V)5LB\n4KJ)L5J\nN3R)Z7F\n72Z)Q4K")

(def solve-part-one
  (calculate-checksum (build-orbit-tree puzzle-input :child)))

(def solve-part-two
  (steps-between "YOU" "SAN" (build-orbit-tree puzzle-input :parent)))