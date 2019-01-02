import java.util.NoSuchElementException;

/**
 * StringTokenizer �N���X���g�p����ƁA�A�v���P�[�V�����ŕ�������g�[�N���ɕ����ł��܂��B
 * �g�[�N�����̃��\�b�h�́AStreamTokenizer �N���X�Ŏg�p����Ă��郁�\�b�h��������ɊȒP�ł��B
 * StreamTokenizer ���\�b�h�ł́A���ʎq�A�����A���p������͋�ʂ���܂���B
 * �܂��A�R�����g��F�����ăX�L�b�v���邱�Ƃ�����܂���B<br>
 * <br>
 * ��؂蕶�� (�g�[�N���𕪂��镶��) �́A�N���X�̍쐬���A�܂��̓g�[�N���𓾂邽�тɎw��ł��܂��B<br>
 * <br>
 * StringTokenizer �̃C���X�^���X�́A�쐬���� returnDelims �t���O�̒l�� true �� false ���ɂ���āA���삪�قȂ�܂��B
 * <ul>
 *  <li>�t���O�� false �̏ꍇ�́A��؂蕶���̓g�[�N���𕪂��邾���̂��̂ƌ��Ȃ����B
 *      �g�[�N���͋�؂蕶���łȂ������� 1 �ȏ�A�����Ă��镔���ł���</li>
 *  <li>�t���O�� true �̏ꍇ�́A��؂蕶���͂��ꎩ�̂��g�[�N���ƌ��Ȃ����B
 *      �g�[�N���́A1 �̋�؂蕶�����A��؂蕶���łȂ������� 1 �ȏ�A�����Ă��镔���ł���</li>
 * </ul>
 * StringTokenizer �I�u�W�F�N�g�͓����I�ɁA�g�[�N��������镶������̌��݂̈ʒu���Ǘ����܂��B
 * �������̃I�y���[�V�����́A���̌��݂̈ʒu���������ꂽ�����̐�ɐi�߂���̂�����܂��B<br>
 * <br>
 * �g�[�N���́AStringTokenizer �I�u�W�F�N�g���쐬����̂Ɏg�p���ꂽ������̕�����������擾���邱�Ƃɂ���ĕԂ���܂��B<br>
 * <br>
 * �g�p������Ɏ����܂��B
 * <blockquote>
 *  <pre>
 *   StringTokenizer st = new StringTokenizer("this is a test");
 *   while (st.hasMoreTokens()) {
 *     println(st.nextToken());
 *   }
 *  </pre>
 * </blockquote>
 * ��ʂɂ͎��̂悤�ɕ\������܂��B
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
  // �f�t�H���g�̃f���~�^
  static final private String DEFAULT_DELIM = " \t\n\r\f";
  
  // ���̓f�[�^
  String str;
  // �f���~�^
  char[] delims;
  // �f���~�^���P�̃g�[�N���Ƃ��Ĉ������ǂ���
  boolean returnDelims;
  
  // ���݂̌����J�n�ʒu
  int index;
  
  /**
   * �w�肳�ꂽ������ɑ΂��� StringTokenizer ���쐬���܂��B
   * delim �������̂��ׂĂ̕����́A�g�[�N������؂邽�߂̋�؂蕶���ł��B<br>
   * <br>
   * returnDelims �t���O�� true �̏ꍇ�́A��؂蕶�����g�[�N���Ƃ��ĕԂ���܂��B
   * �e��؂蕶���͒��� 1 �̕�����Ƃ��ĕԂ���܂��B�t���O�� false �̏ꍇ�́A��؂蕶���̓X�L�b�v����A
   * �g�[�N���𕪂��邾���̂��̂ƌ��Ȃ���܂��B
   * @param str ��͂���镶����
   * @param delim ��؂蕶��
   * @param returnDelims ��؂蕶�����g�[�N���Ɋ܂߂邩�ǂ����������t���O
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
   * �w�肳�ꂽ������ɑ΂��� StringTokenizer ���쐬���܂��B
   * delim �������̂��ׂĂ̕����́A�g�[�N������؂邽�߂̋�؂蕶���ł��B
   * ��؂蕶�����̂��̂��g�[�N���Ƃ��Ĉ����邱�Ƃ͂���܂���B
   * @param str ��͂���镶����
   * @param delim ��؂蕶��
   */
  public StringTokenizer(String str, String delim) {
    this(str, delim, false);
  }
  
  /**
   * �w�肳�ꂽ������ɑ΂��� StringTokenizer ���쐬���܂��B
   * �g�[�N�i�C�U�̓f�t�H���g�̋�؂蕶���Z�b�g���g���܂��B
   * ���̃Z�b�g�� " \t\n\r\f" �ŁA�󔒕����A�^�u�����A���s�����A���A���s�����A����їp�����蕶������\������܂��B
   * ��؂蕶���́A���ꎩ�̂��g�[�N���Ƃ��ď�������邱�Ƃ͂���܂���B 
   * @param str ��͂���镶����
   */
  public StringTokenizer(String str) {
    this(str, DEFAULT_DELIM);
  }
  
  /**
   * �g�[�N�i�C�U�̕�����ŗ��p�ł���g�[�N�����܂����邩�ǂ����𔻒肵�܂��B
   * ���̃��\�b�h�� true ��Ԃ��ꍇ�A����ȍ~�̈����̂Ȃ� nextToken �ւ̌Ăяo���͓K�؂Ƀg�[�N����Ԃ��܂��B
   * @return ��������̌��݂̈ʒu�̌��� 1 �ȏ�̃g�[�N��������ꍇ���� true�A�����łȂ��ꍇ�� false
   */
  public boolean hasMoreTokens() {
    // nextToken�ŗ�O���o�Ȃ���� true ��Ԃ�
    try {
      nextToken(false);
      return true;
    } catch (NoSuchElementException nsee) {
      return false;
    }
  }
  
  /**
   * ������g�[�N�i�C�U���玟�̃g�[�N����Ԃ��܂��B
   * @return ������g�[�N�i�C�U����̎��̃g�[�N��
   * @exception NoSuchElementException �g�[�N�i�C�U�̕�����Ƀg�[�N�����c���Ă��Ȃ��ꍇ
   */
  public String nextToken() {
    return nextToken(true);
  }
  
  /**
   * �g�[�N�����������̎���
   * @param countIndex �g�[�N���擾���index��i�߂邩�ǂ���
   * @exception NoSuchElementException �g�[�N�i�C�U�̕�����Ƀg�[�N�����c���Ă��Ȃ��ꍇ
   */
  private String nextToken(boolean countIndex) {
    // ���ɕ����܂Ō������I����Ă���ꍇ�̓g�[�N���͖���
    if ( index >= str.length() ) {
      throw new NoSuchElementException();
    }
    
    int tmp = 0;
    
    // �����������END�ʒu����͕�����̍Ō�ɐݒ�
    int endIndex = str.length();
    
    // �e�f���~�^�̈ʒu�𒲍�����
    for ( int i = 0; i < delims.length; i++ ) {
      tmp = str.indexOf((int)delims[i], index);
      if ( (tmp >= 0) && (tmp < endIndex) ) {
        endIndex = tmp;
      }
    }
    
    String ret = null;
    
    // �g�[�N���Ƃ��Ẵf���~�^�𔭌������ꍇ�̓f���~�^��Ԃ�
    if ( returnDelims && (index == endIndex) ) {
      ret = str.substring(index, endIndex + 1);
      if ( countIndex ) index++;
    }
    // ���������g�[�N����Ԃ�
    else {
      // �g�[�N���̐؂�o��
      ret = str.substring(index, endIndex);
      
      // �f���~�^���g�[�N���Ƃ��Ĉ����ꍇ
      if ( returnDelims ) {
        if ( countIndex ) index = endIndex;
      }
      // �f���~�^�̓g�[�N���Ƃ��Ĉ���Ȃ��ꍇ�͔�΂�
      else {
        if ( countIndex ) {
          index = endIndex + 1;
          
          // �����f���~�^��ǂݔ�΂�����
          boolean isEnd = false;
          for ( ; !isEnd && (index < str.length()); ) {
            for ( int i = 0; i < delims.length; i++ ) {
              if ( str.charAt(index) == delims[i] ) {
                // �f���~�^���������ꂽ�ꍇ��index��i�߂�
                index++;
                break;
              }
              // �f���~�^����������Ȃ������ꍇ�͏����I��
              isEnd = true;
            }
          }
        }
      }
    }
    
    return ret;
  }
  
  /**
   * ������g�[�N�i�C�U�̕����񂩂玟�̃g�[�N����Ԃ��܂��B
   * �ŏ��ɁAStringTokenizer �I�u�W�F�N�g����؂蕶���ƌ��Ȃ������̃Z�b�g���A������ delim ���̕����ɂȂ�悤�ɕύX����܂��B
   * ���ɁA��������̌��݂̈ʒu�̌��ɂ��鎟�̃g�[�N�����Ԃ���܂��B
   * ���݂̈ʒu�́A�F�����ꂽ�g�[�N�����z���Đi�݂܂��B
   * �w�肳�ꂽ��؂蕶���̏W���́A���̌Ăяo���̂��Ƃ��f�t�H���g�l�Ƃ��Ďg�p����܂��B
   * @param delim �V������؂蕶��
   * @return �V������؂蕶���ɐ؂�ւ������Ƃ̃g�[�N��
   * @exception NoSuchElementException �g�[�N�i�C�U�̕�����Ƀg�[�N�����c���Ă��Ȃ��ꍇ
   */
  public String nextToken(String delim) {
    if ( delim == null ) {
      throw new NullPointerException();
    }
    
    // �f���~�^��ύX
    this.delims = delim.toCharArray();
    
    return nextToken();
  }
  
  /**
   * ��O�𐶐������Ƀg�[�N�i�C�U�� nextToken ���\�b�h���Ăяo����񐔂��v�Z���܂��B���݂̈ʒu�͐i�݂܂���B
   * @return ���݂̋�؂蕶����K�p�����Ƃ��ɕ�����Ɏc���Ă���g�[�N���̐�
   * @see #nextToken()
   */
  public int countTokens() {
    int count = 0;
    
    // ���݂�index��ޔ�
    int tmp = index;
    
    // ���ۂɃg�[�N�������擾�ł��邩�J�E���g���Ă݂�
    try {
      while(true) {
        nextToken(true);
        count++;
      }
    } catch (NoSuchElementException nsee) {}
    
    // �ޔ����Ă���index�𕜋A
    index = tmp;
    
    return count;
  }
  
  // Enumeration�C���^�[�t�F�[�X�p
  /**
   * hasMoreTokens ���\�b�h�Ɠ����l��Ԃ��܂��B
   * ������g�p����ƁA���̃N���X�� Enumeration �C���^�t�F�[�X���������邱�Ƃ��ł��܂��B
   * @return �g�[�N�i�C�U������ŗ��p�ł���g�[�N�����܂�����ꍇ�� true�A�����łȂ��ꍇ�� false
   * @exception NoSuchElementException �g�[�N�i�C�U�̕�����Ƀg�[�N�����c���Ă��Ȃ��ꍇ
   * @see java.util.Enumeration
   * @see #hasMoreTokens()
   */
  public boolean hasMoreElements() {
    return hasMoreTokens();
  }
  
  /**
   * nextToken ���\�b�h�Ɠ����l��Ԃ��܂��B
   * �������A�錾���ꂽ�߂�l�́AString �ł͂Ȃ� Object �ł��B
   * ������g�p���Ă��̃N���X�� Enumeration �C���^�t�F�[�X���������邱�Ƃ��ł��܂��B
   * @return ������̎��̃g�[�N��
   * @exception NoSuchElementException �g�[�N�i�C�U�̕�����Ƀg�[�N�����c���Ă��Ȃ��ꍇ
   * @see java.util.Enumeration
   * @see #nextToken()
   */
  public Object nextElement() {
    return nextToken();
  }
}
