import java.util.NoSuchElementException;

/**
 * StringTokenizer クラスを使用すると、アプリケーションで文字列をトークンに分割できます。
 * トークン化のメソッドは、StreamTokenizer クラスで使用されているメソッドよりもさらに簡単です。
 * StreamTokenizer メソッドでは、識別子、数字、引用文字列は区別されません。
 * また、コメントを認識してスキップすることもありません。<br>
 * <br>
 * 区切り文字 (トークンを分ける文字) は、クラスの作成時、またはトークンを得るたびに指定できます。<br>
 * <br>
 * StringTokenizer のインスタンスは、作成時の returnDelims フラグの値が true か false かによって、動作が異なります。
 * <ul>
 *  <li>フラグが false の場合は、区切り文字はトークンを分けるだけのものと見なされる。
 *      トークンは区切り文字でない文字が 1 個以上連続している部分である</li>
 *  <li>フラグが true の場合は、区切り文字はそれ自体がトークンと見なされる。
 *      トークンは、1 個の区切り文字か、区切り文字でない文字が 1 個以上連続している部分である</li>
 * </ul>
 * StringTokenizer オブジェクトは内部的に、トークン化される文字列内の現在の位置を管理します。
 * いくつかのオペレーションは、この現在の位置を処理された文字の先に進めるものがあります。<br>
 * <br>
 * トークンは、StringTokenizer オブジェクトを作成するのに使用された文字列の部分文字列を取得することによって返されます。<br>
 * <br>
 * 使用例を次に示します。
 * <blockquote>
 *  <pre>
 *   StringTokenizer st = new StringTokenizer("this is a test");
 *   while (st.hasMoreTokens()) {
 *     println(st.nextToken());
 *   }
 *  </pre>
 * </blockquote>
 * 画面には次のように表示されます。
 * 
 * <blockquote>
 *  <pre>
 *   this
 *   is
 *   a
 *   test
 *  </pre>
 * </blockquote>
 * 
 * @see "java.util.StringTokenizer (J2SE)"
 * @version 1.0 (2008/09/28)
 */
public class StringTokenizer implements java.util.Enumeration {
  // デフォルトのデリミタ
  static final private String DEFAULT_DELIM = " \t\n\r\f";
  
  // 入力データ
  String str;
  // デリミタ
  char[] delims;
  // デリミタを１つのトークンとして扱うかどうか
  boolean returnDelims;
  
  // 現在の検索開始位置
  int index;
  
  /**
   * 指定された文字列に対する StringTokenizer を作成します。
   * delim 引数内のすべての文字は、トークンを区切るための区切り文字です。<br>
   * <br>
   * returnDelims フラグが true の場合は、区切り文字もトークンとして返されます。
   * 各区切り文字は長さ 1 の文字列として返されます。フラグが false の場合は、区切り文字はスキップされ、
   * トークンを分けるだけのものと見なされます。
   * @param str 解析される文字列
   * @param delim 区切り文字
   * @param returnDelims 区切り文字をトークンに含めるかどうかを示すフラグ
   */
  public StringTokenizer(String str, String delim, boolean returnDelims) {
    if ( (str == null) || (delim == null) ) {
      throw new NullPointerException();
    }
    
    this.str = str;
    this.delims = delim.toCharArray();
    this.returnDelims = returnDelims;
    index = 0;
  }
  
  /**
   * 指定された文字列に対する StringTokenizer を作成します。
   * delim 引数内のすべての文字は、トークンを区切るための区切り文字です。
   * 区切り文字そのものがトークンとして扱われることはありません。
   * @param str 解析される文字列
   * @param delim 区切り文字
   */
  public StringTokenizer(String str, String delim) {
    this(str, delim, false);
  }
  
  /**
   * 指定された文字列に対する StringTokenizer を作成します。
   * トークナイザはデフォルトの区切り文字セットを使います。
   * このセットは " \t\n\r\f" で、空白文字、タブ文字、改行文字、復帰改行文字、および用紙送り文字から構成されます。
   * 区切り文字は、それ自体がトークンとして処理されることはありません。 
   * @param str 解析される文字列
   */
  public StringTokenizer(String str) {
    this(str, DEFAULT_DELIM);
  }
  
  /**
   * トークナイザの文字列で利用できるトークンがまだあるかどうかを判定します。
   * このメソッドが true を返す場合、それ以降の引数のない nextToken への呼び出しは適切にトークンを返します。
   * @return 文字列内の現在の位置の後ろに 1 つ以上のトークンがある場合だけ true、そうでない場合は false
   */
  public boolean hasMoreTokens() {
    // nextTokenで例外が出なければ true を返す
    try {
      nextToken(false);
      return true;
    } catch (NoSuchElementException nsee) {
      return false;
    }
  }
  
  /**
   * 文字列トークナイザから次のトークンを返します。
   * @return 文字列トークナイザからの次のトークン
   * @exception NoSuchElementException トークナイザの文字列にトークンが残っていない場合
   */
  public String nextToken() {
    return nextToken(true);
  }
  
  /**
   * トークン検索処理の実体
   * @param countIndex トークン取得後にindexを進めるかどうか
   * @exception NoSuchElementException トークナイザの文字列にトークンが残っていない場合
   */
  private String nextToken(boolean countIndex) {
    // 既に文末まで検索し終わっている場合はトークンは無し
    if ( index >= str.length() ) {
      throw new NoSuchElementException();
    }
    
    int tmp = 0;
    
    // 部分文字列のEND位置を入力文字列の最後に設定
    int endIndex = str.length();
    
    // 各デリミタの位置を調査する
    for ( int i = 0; i < delims.length; i++ ) {
      tmp = str.indexOf((int)delims[i], index);
      if ( (tmp >= 0) && (tmp < endIndex) ) {
        endIndex = tmp;
      }
    }
    
    String ret = null;
    
    // トークンとしてのデリミタを発見した場合はデリミタを返す
    if ( returnDelims && (index == endIndex) ) {
      ret = str.substring(index, endIndex + 1);
      if ( countIndex ) index++;
    }
    // 発見したトークンを返す
    else {
      // トークンの切り出し
      ret = str.substring(index, endIndex);
      
      // デリミタもトークンとして扱う場合
      if ( returnDelims ) {
        if ( countIndex ) index = endIndex;
      }
      // デリミタはトークンとして扱わない場合は飛ばす
      else {
        if ( countIndex ) {
          index = endIndex + 1;
          
          // 続くデリミタを読み飛ばす処理
          boolean isEnd = false;
          for ( ; !isEnd && (index < str.length()); ) {
            for ( int i = 0; i < delims.length; i++ ) {
              if ( str.charAt(index) == delims[i] ) {
                // デリミタが発見された場合はindexを進める
                index++;
                break;
              }
              // デリミタが発見されなかった場合は処理終了
              isEnd = true;
            }
          }
        }
      }
    }
    
    return ret;
  }
  
  /**
   * 文字列トークナイザの文字列から次のトークンを返します。
   * 最初に、StringTokenizer オブジェクトが区切り文字と見なす文字のセットが、文字列 delim 内の文字になるように変更されます。
   * 次に、文字列内の現在の位置の後ろにある次のトークンが返されます。
   * 現在の位置は、認識されたトークンを越えて進みます。
   * 指定された区切り文字の集合は、この呼び出しのあともデフォルト値として使用されます。
   * @param delim 新しい区切り文字
   * @return 新しい区切り文字に切り替えたあとのトークン
   * @exception NoSuchElementException トークナイザの文字列にトークンが残っていない場合
   */
  public String nextToken(String delim) {
    if ( delim == null ) {
      throw new NullPointerException();
    }
    
    // デリミタを変更
    this.delims = delim.toCharArray();
    
    return nextToken();
  }
  
  /**
   * 例外を生成せずにトークナイザの nextToken メソッドを呼び出せる回数を計算します。現在の位置は進みません。
   * @return 現在の区切り文字を適用したときに文字列に残っているトークンの数
   * @see #nextToken()
   */
  public int countTokens() {
    int count = 0;
    
    // 現在のindexを退避
    int tmp = index;
    
    // 実際にトークンを何個取得できるかカウントしてみる
    try {
      while(true) {
        nextToken(true);
        count++;
      }
    } catch (NoSuchElementException nsee) {}
    
    // 退避していたindexを復帰
    index = tmp;
    
    return count;
  }
  
  // Enumerationインターフェース用
  /**
   * hasMoreTokens メソッドと同じ値を返します。
   * これを使用すると、このクラスに Enumeration インタフェースを実装することができます。
   * @return トークナイザ文字列で利用できるトークンがまだある場合は true、そうでない場合は false
   * @exception NoSuchElementException トークナイザの文字列にトークンが残っていない場合
   * @see java.util.Enumeration
   * @see #hasMoreTokens()
   */
  public boolean hasMoreElements() {
    return hasMoreTokens();
  }
  
  /**
   * nextToken メソッドと同じ値を返します。
   * ただし、宣言された戻り値は、String ではなく Object です。
   * これを使用してこのクラスに Enumeration インタフェースを実装することができます。
   * @return 文字列の次のトークン
   * @exception NoSuchElementException トークナイザの文字列にトークンが残っていない場合
   * @see java.util.Enumeration
   * @see #nextToken()
   */
  public Object nextElement() {
    return nextToken();
  }
}
